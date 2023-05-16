sealed trait Expr
case class Num(value: Int) extends Expr
case class Sum(left: Expr, right: Expr) extends Expr
case class Product(left: Expr, right: Expr) extends Expr
case class Var(name: String) extends Expr

def eval(expr: Expr, vars: Map[String, Expr]): Int = expr match {
  case Num(value) =>
    value
  case Var(name) =>
    eval(vars(name), vars)
  case Sum(left, right) =>
    eval(left, vars) + eval(right, vars)
  case Product(left, right) =>
    eval(left, vars) * eval(right, vars)
}

def main(args: Array[String]): Unit = {
  val expr = Product(Var("x"), Product(Num(1), Num(3)))
  val vars = Map("x" -> Sum(Num(2), Num(3)))
  println(eval(expr, vars))
}
