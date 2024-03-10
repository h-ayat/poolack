package dive.poolack.api

trait Id extends Any {
  def value: String
}

final case class IssueId(value: String) extends AnyVal with Id
final case class LabelId(value: String) extends AnyVal with Id
final case class UserId(value: String) extends AnyVal with Id
final case class TaskId(value: String) extends AnyVal with Id

sealed trait IssueState
object IssueState {
  case object Pending extends IssueState
  case object Active extends IssueState
  case object Closed extends IssueState
}

final case class Issue(
    issueId: String,
    tasks: List[Task],
    title: String,
    description: String,
    assignee: User,
    author: User,
    state: IssueState
)

final case class Task(
    id: TaskId,
    title: String,
    description: String,
    assignee: User,
    labels: List[Label],
    author: User,
    state: TaskState
)

final case class User(
    id: UserId,
    fullName: String,
    icon: String
)

final case class Label(id: LabelId, color: Int, name: String)

sealed trait TaskState
object TaskState {
  case object Pending extends TaskState
  case object Active extends TaskState
  case object PreRelease extends TaskState
  case object Done extends TaskState

  val all: List[TaskState] = Pending :: Active :: PreRelease :: Done :: Nil
}

/** Issue -> branch, assignee
  *
  * Task -> assignee state -> pending, active, preRelease -> Done
  */

object Test {

  def test(l: Label): Unit = {

    getIssuesByLabel(l.id)
  }

  def getIssuesByLabel(labelId: LabelId): List[Issue] = ???
}
