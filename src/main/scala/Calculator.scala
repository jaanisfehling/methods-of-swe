trait Expression
case class Constant(value: AnyVal) extends Expression
case class Variable(name: String) extends Expression

trait Operator extends Expression
case class Sum(left: Expression, right: Expression) extends Operator
case class Product(left: Expression, right: Expression) extends Operator
case class And(left: Expression, right: Expression) extends Operator
case class Or(left: Expression, right: Expression) extends Operator

trait Command
case class Assign(variable: Variable, expression: Expression) extends Command
case class Print(expression: Expression) extends Command



def eval(expr: Expression, vars: Map[String, Expression]): Int = expr match
  case Constant(value) =>
    value
  case Variable(name) =>
    eval(vars(name), vars)
  case Sum(left, right) =>
    eval(left, vars) + eval(right, vars)
  case Product(left, right) =>
    eval(left, vars) * eval(right, vars)


@main def main(): Unit =
  val expr = Product(Variable("x"), Product(Constant(1), Constant(3)))
  val vars = Map("x" -> Sum(Constant(2), Constant(3)))
  println(eval(expr, vars))
