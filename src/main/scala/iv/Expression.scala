package iv

import iv.Operations.Operator

/**
  * Created by Florin-Gabriel Barbuceanu on 16/08/2017.
  */
sealed trait Expression {
  def value: Int
}

final case class Value(value: Int) extends Expression {
  override def toString: String = if (value < 0) s"(${value.toString})" else value.toString
}

final case class Computable(lhs: Expression, operator: Operator, rhs: Expression) extends Expression {

  override def value: Int = operator(lhs, rhs).value

  override def toString: String = s"($lhs ${operator.symbol} $rhs)"
}
