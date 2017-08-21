package iv

import iv.Operations.Operator

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

/**
  * Created by Florin-Gabriel Barbuceanu on 18/08/2017.
  */
class ExpressionGenerator private() {

  def buildExpression(terms: List[Int], result: Int): Future[Expression] = {
    val promise: Promise[Expression] = Promise()
    Future {
      findMatchingExpression(terms, Nil, result, promise)
    } flatMap {
      case Some(_) => promise.future
      case None => promise.failure(new IllegalArgumentException(s"Cannot build a solution using $terms")).future
    }
  }

  @tailrec
  private def findMatchingExpression(terms: List[Int], expressions: List[Expression], result: Int, promise: Promise[Expression]): Option[Expression] = {
    val newExpressions: List[Expression] = generateExpressions(terms.head, expressions)
    val targetExpression: Option[Expression] = newExpressions.find(_.value == result)

    if (targetExpression.isDefined) {
      if (!promise.isCompleted) {
        promise.success(targetExpression.get)
      }
      targetExpression
    } else {
      if (terms.tail == Nil) Option.empty
      else findMatchingExpression(terms.tail, expressions ::: newExpressions, result, promise)
    }
  }

  private def generateExpressions(newTerm: Int, expressions: List[Expression]): List[Expression] = {
    Value(newTerm) :: expressions.flatMap(generateAllOperations(_, newTerm))
  }

  private def generateAllOperations(lhs: Expression, newTerm: Int): List[Expression] = {
    def generateOne(operator: Operator) = Computable(lhs, operator, Value(newTerm))

    val deadBranch: Expression => Boolean = _.value > 0

    Operations
      .all
      .map(generateOne)
      .filter(deadBranch)
  }
}

object ExpressionGenerator {
  lazy val instance = new ExpressionGenerator()

  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      println(s"Usage:: program [terms] result")
      System.exit(-1)
    }
    val terms: List[Int] = args.dropRight(1).map(_.toInt).to
    val result = args.last.toInt

    instance
      .buildExpression(terms, result)
      .onComplete(println)

    // prevent the main thread from exiting early
    System.in.read()
  }
}