package dive.poolack.api

import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import dive.poolack.api.IssueState.Pending
import dive.poolack.api.IssueState.Active
import dive.poolack.api.IssueState.Closed

trait IssueApi {

  def newIssue(param: NewIssue.Param)(implicit
      ec: ExecutionContext
  ): Future[Either[NewIssue.Error, IssueId]]

  def addTask(issueId: IssueId, param: AddTaskToIssue.Param)(implicit
      ec: ExecutionContext
  ): Future[Either[AddTaskToIssue.Error, TaskId]]

  /*

  getByAssignee
  getByAuthor
  transition -> Issue , Task

  create label
  update label
  update Issue
  Update task


   */

  // def testIssue(i: Issue): Int = {
  //   i.state match {
  //     case Pending =>
  //       0
  //     case Active =>
  //       1
  //     case Closed =>
  //       throw new Exception("Error here")
  //   }
  // }
}

object NewIssue {
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
