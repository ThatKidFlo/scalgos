package iv

import org.scalatest.{AsyncFlatSpec, ParallelTestExecution}

import scala.concurrent.Future
import scala.util.Failure

/**
  * Created by Florin-Gabriel Barbuceanu on 20/08/2017.
  */
class ExpressionsSuite extends AsyncFlatSpec with ParallelTestExecution {
  def generateExpression(terms: List[Int], result: Int): Future[Expression] = ExpressionGenerator
    .instance
    .buildExpression(terms, result)

  "Expression generator" should "successfully generate an expression matching the result asynchronously" in {
    generateExpression(List(2, 3, 5, 6, 8, 8, 42, 12, -21, -42), 42)
      .map(expr => assert(expr == Computable(Computable(Value(2), Operations.Add, Value(5)), Operations.Multiply, Value(6))))
  }

  it should "generate the very first matching expression asynchronously" in {
    generateExpression(List(42, 2, 3, 5, 6, 8, 8, 42, 12, -21, -42), 42)
      .map(expr => assert(expr == Value(42)))
  }

  it should "return a descriptive error in case an expression cannot be generated" in {
    val terms = List(2, 3)

    generateExpression(terms, 42).transformWith {
      case Failure(err) => Future(assert(err.getMessage == s"Cannot build a solution using $terms"))
      case _ => Future(assert(false))
    }
  }
}
