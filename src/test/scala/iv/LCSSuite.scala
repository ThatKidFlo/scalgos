package iv

import org.scalatest.FlatSpec

/**
  * Created by Florin-Gabriel Barbuceanu on 20/08/2017.
  */
class LCSSuite extends FlatSpec {

  "Longest Common Subsequence calculator" should "find the longest common subsequence of a two strings" in {
    assert(LCS.lcs("AABACDA", "DACBBCAD") == "ABCA")
  }

  it should "work for any T for which there exists an implicit Ordering[T]" in {
    assert(LCS.lcsInt(List(1, 2, 3, 4), List(1, 4, 5, 3, 2, 3, 4)) == List(1, 2, 3, 4))
  }
}
