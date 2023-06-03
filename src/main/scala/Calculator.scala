import scala.reflect.ClassTag

trait Expression
case class Constant(value: Int | Boolean) extends Expression
case class Variable(name: String) extends Expression

trait Operator extends Expression
case class Sum(left: Expression, right: Expression) extends Operator
case class Product(left: Expression, right: Expression) extends Operator
case class And(left: Expression, right: Expression) extends Operator
case class Or(left: Expression, right: Expression) extends Operator

trait Command
case class Assign(variable: Variable, expression: Expression) extends Command
case class Print(expression: Expression) extends Command

def isTypeCorrect(a: Int | Boolean, b: Int | Boolean): Boolean =
  val B = ClassTag(b.getClass)
  ClassTag(a.getClass) match
    case B => true
    case _ => false

def eval(expr: Expression, vars: Map[String, Expression]): Int | Boolean = expr match
  case Constant(value) =>
    value
  case Variable(name) =>
    eval(vars(name), vars)
  case Sum(left, right) =>
    eval(left, vars) + eval(right, vars)
  case Product(left, right) =>
    eval(left, vars) * eval(right, vars)
  case And(left, right) =>
    eval(left, vars) & eval(right, vars)
  case Or(left, right) =>
    eval(left, vars) | eval(right, vars)

@main def main(): Unit =
  val expr = Product(Variable("x"), Product(Constant(1), Constant(3)))
  val vars = Map("x" -> Sum(Constant(2), Constant(3)), "isTrue" -> Or(Constant(true), Constant(false)))
  println(eval(expr, vars))
