# Scalgos


## Assumptions

1. The implementation of the first algorithm could be further improved by using memoization.
2. The implementation of the second algorithm will not check if the input is valid (i.e. it relies on the fact that all arguments given as input are valid)
3. You are aware of the limits imposed on writing `clean code` when implementing an algorithm (realistically, it would be one of the `hearts` of a system, and higher-level abstractions would be built atop)

## Longest Common Subsequence (LCS)
Given two strings, find their longest common subsequence.

Example:

_Input_: `"AABACDA" "DACBBCAD"`

_Expected output_: `"ABCA"`

The implementation is based on the [recursive LCS algorithm](https://en.wikipedia.org/wiki/Longest_common_subsequence_problem#LCS_function_defined)

It will not behave well for very large inputs, because of its recursive implementation (very likely to overflow the stack).

## Expression
Given a sequence of `N` integers, create a mathematical expression out of the first `N-1` which is equal to the last integer.
All intermediate results should evaluate to positive integers.

Example:

_Input_: `2 3 5 6 8 8 42 12 -21 -42 42`

_Expected output_: `((2 + 5) * 6)`

The implementation is inspired by the Monte Carlo method.

The stateless service `ExpressionGenerator` will create a background task that handles generation of expressions, and it
will return a `Future[Expression]`, derived from a `Promise`. The algorithm is short circuiting in the sense that once the
first result is found, all further computation is halted.

An expression branch will stop being evaluated once the algorithm determines it yields a negative/zero value, in order 
to optimize computation.
