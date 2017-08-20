package iv

/**
  * Created by Florin-Gabriel Barbuceanu on 16/08/2017.
  */
class LCS[T](implicit t: Ordering[T]) {
  def findLongestCommonSubsequence(left: List[T], right: List[T]): List[T] = {
    _lcs(left, right)
  }

  private def _lcs(left: List[T], right: List[T]): List[T] = left -> right match {
    case (_, Nil) | (Nil, _) => Nil
    case (leftHead :: leftTail, rightHead :: rightTail) if leftHead == rightHead => leftHead :: _lcs(leftTail, rightTail)
    case (_ :: leftTail, _ :: rightTail) =>
      val xs = _lcs(left, rightTail)
      val ys = _lcs(leftTail, right)

      if (xs.length > ys.length) xs else ys
  }
}

object LCS {
  lazy val charProcessor: LCS[Char] = new LCS
  lazy val intProcessor: LCS[Int] = new LCS

  def lcs(left: String, right: String): String = {
    charProcessor.findLongestCommonSubsequence(left.to, right.to).mkString
  }

  def lcsInt(left: List[Int], right: List[Int]): List[Int] = {
    intProcessor.findLongestCommonSubsequence(left, right)
  }
}