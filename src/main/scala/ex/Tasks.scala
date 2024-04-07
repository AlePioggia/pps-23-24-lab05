// package ex

// import scala.annotation.targetName

// // Express a second degree polynomial
// // Structure: secondDegree * X^2 + firstDegree * X + constant
// trait SecondDegreePolynomial:
//   def constant: Double
//   def firstDegree: Double
//   def secondDegree: Double
//   def +(polynomial: SecondDegreePolynomial): SecondDegreePolynomial
//   def -(polynomial: SecondDegreePolynomial): SecondDegreePolynomial

// object SecondDegreePolynomial:
//   def apply(
//       secondDegree: Double,
//       firstDegree: Double,
//       constant: Double
//   ): SecondDegreePolynomial =
//     SecondDegreePolynomialImpl(constant, firstDegree, secondDegree)
//   private case class SecondDegreePolynomialImpl(
//       override val constant: Double,
//       override val firstDegree: Double,
//       override val secondDegree: Double
//   ) extends SecondDegreePolynomial:
//     override def +(polynomial: SecondDegreePolynomial): SecondDegreePolynomial =
//       SecondDegreePolynomialImpl(
//         constant + polynomial.constant,
//         firstDegree + polynomial.firstDegree,
//         secondDegree + polynomial.secondDegree
//       )
//     override def -(polynomial: SecondDegreePolynomial): SecondDegreePolynomial =
//       SecondDegreePolynomialImpl(
//         constant - polynomial.constant,
//         firstDegree - polynomial.firstDegree,
//         secondDegree - polynomial.secondDegree
//       )
