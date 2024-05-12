package dive.poolack.api

import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import dive.poolack.api.IssueState.Pending
import dive.poolack.api.IssueState.Active
import dive.poolack.api.IssueState.Closed
import dive.poolack.util.IO

trait IssueApi {

  def newIssue(param: NewIssue.Param)(implicit
      ec: ExecutionContext
  ): IO[NewIssue.Error, NewIssue.Result]


  def addTask(issueId: IssueId, param: AddTaskToIssue.Param)(implicit
      ec: ExecutionContext
  ): IO[AddTaskToIssue.Error, TaskId]

}

object NewIssue {

  final case class Result(issueId: IssueId)

  final case class Param(
      title: String,
      description: Option[String],
      assignee: Option[UserId]
  )

  sealed trait Error
  object Error {
    final case class StringValidation(msg: String) extends Error
    final case class UserNotFound(id: UserId) extends Error
  }
}

object AddTaskToIssue {
  final case class Param(
      title: String,
      description: Option[String],
      assignee: Option[UserId],
      labels: List[LabelId]
  )

  sealed trait Error
  object Error {
    final case class StringValidation(msg: String) extends Error
    final case class UserNotFound(id: UserId) extends Error
    final case class LabelNotFound(id: LabelId) extends Error
  }

}
