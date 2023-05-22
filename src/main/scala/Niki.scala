import scala.annotation.tailrec

trait Command
case class Left() extends Command
case class Right() extends Command
case class Forward(number: Int) extends Command

enum Direction:
  case North, East, South, West

case class State(position: (Int, Int), direction: Direction)

@tailrec
def execute(cmds: Seq[Command], state: State): State =
  if cmds.isEmpty then state
  else cmds.head match {
    case Left() => state match {
      case State(position, Direction.North) =>
        execute(cmds.drop(1), State(position, Direction.West))
      case State(position, Direction.East) =>
        execute(cmds.drop(1), State(position, Direction.North))
      case State(position, Direction.South) =>
        execute(cmds.drop(1), State(position, Direction.East))
      case State(position, Direction.West) =>
        execute(cmds.drop(1), State(position, Direction.South))
    }
    case Right() => state match {
      case State(position, Direction.North) =>
        execute(cmds.drop(1), State(position, Direction.East))
      case State(position, Direction.East) =>
        execute(cmds.drop(1), State(position, Direction.South))
      case State(position, Direction.South) =>
        execute(cmds.drop(1), State(position, Direction.West))
      case State(position, Direction.West) =>
        execute(cmds.drop(1), State(position, Direction.North))
    }
    case Forward(n) => state match {
      case State(position, Direction.North) =>
        execute(cmds.drop(1), State((position(0), position(1)+n), Direction.North))
      case State(position, Direction.East) =>
        execute(cmds.drop(1), State((position(0)+n, position(1)), Direction.East))
      case State(position, Direction.South) =>
        execute(cmds.drop(1), State((position(0), position(1)-n), Direction.South))
      case State(position, Direction.West) =>
        execute(cmds.drop(1), State((position(0)-n, position(1)), Direction.West))
    }
}

@main def main() =
  val state = State((0, 0), Direction.North)
  val program = List(Forward(1), Left(), Forward(2))
  val finalState = execute(program, state)
  println(finalState.position(0))
