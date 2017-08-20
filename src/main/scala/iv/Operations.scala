package iv

/**
  * Created by Florin-Gabriel Barbuceanu on 18/08/2017.
  */

object Operations {

  sealed trait Operator {
    def apply(lhs: Expression, rhs: Expression): Expression = {
      operation(lhs, rhs)
    }

    def symbol: String

    protected def operation: (Expression, Expression) => Expression
  }

  final case object Add extends Operator {
    override def symbol: String = "+"

    override protected def operation: (Expression, Expression) => Expression = (lhs, rhs) => Value(lhs.value + rhs.value)
  }

  final case object Minus extends Operator {
    override def symbol: String = "-"

    override protected def operation: (Expression, Expression) => Expression = (lhs, rhs) => Value(lhs.value - rhs.value)
  }

  final case object Multiply extends Operator {
    override def symbol: String = "*"


    override protected def operation: (Expression, Expression) => Expression = (lhs, rhs) => Value(lhs.value * rhs.value)
  }

  final case object Divide extends Operator {
    override def symbol: String = "/"

    override protected def operation: (Expression, Expression) => Expression = (lhs, rhs) => Value(lhs.value / rhs.value)
  }

  def all: List[Operator] = List(Add, Minus, Multiply, Divide)

  def main(args: Array[String]): Unit = {
    println(Add(Value(3), Value(7)))
    println(Minus(Value(3), Value(7)))
    println(Multiply(Value(3), Value(7)))
    println(Divide(Value(3), Value(7)))
    println(Computable(Value(3), Add, Value(7)))
    println(Computable(Computable(Value(3), Add, Value(7)), Multiply, Computable(Value(3), Add, Value(7))))
    println(Computable(Value(3), Add, Value(7)))
  }
}
