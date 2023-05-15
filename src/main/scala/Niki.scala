trait Command
class Left extends Command
class Right extends Command
class Forward(number: Int) extends Command

enum Direction:
  case North, East, South, West

class State(position: (Int, Int), direction: Direction)

//def execute(cmds: Seq[Command], state: State): State = cmds.head match {
//  case Left() => state match {
//    case State(position, North) =>
//      execute(cmds.drop(1), State(position, West))
//    case State(position, East) =>
//      execute(cmds.drop(1), State(position, North))
//    case State(position, South) =>
//      execute(cmds.drop(1), State(position, East))
//    case State(position, West) =>
//      execute(cmds.drop(1), State(position, South))
//  }
//}
