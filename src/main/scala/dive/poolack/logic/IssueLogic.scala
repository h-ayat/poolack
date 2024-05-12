package dive.poolack.logic

import dive.poolack.api.IssueApi
import dive.poolack.api.{IssueId, NewIssue}
import scala.concurrent.{ExecutionContext, Future}
import dive.poolack.api.{AddTaskToIssue, IssueId, TaskId}
import scala.concurrent.{ExecutionContext, Future}
import dive.poolack.util.IO
import dive.poolack.Issue
import dive.poolack.persist.mongodb.MongoIssueRepo
import java.util.UUID
import dive.poolack.api.UserId

object IssueLogic extends IssueApi {

  override def newIssue(param: NewIssue.Param)(implicit
      ec: ExecutionContext
  ): IO[NewIssue.Error, NewIssue.Result] = {

    val issue =
      Issue(
        _id = IssueId(randomId),
        title = param.title,
        description = param.description,
        assignee = param.assignee
      )

    val result = IO
      .fromFuture(MongoIssueRepo.insert(issue))
      .map(_ => issue)
      .flatMap(_ => userExists(param.assignee.get))
      .filterOrFail(identity)(NewIssue.Error.UserNotFound(param.assignee.get))
      .map(_ => NewIssue.Result(issue._id))

    for {
      _ <- IO.fromFuture(MongoIssueRepo.insert(issue))
      _ <- userExists(param.assignee.get)
        .filterOrFail(identity)(NewIssue.Error.UserNotFound(param.assignee.get))
    } yield NewIssue.Result(issue._id)
    result
  }

  override def addTask(issueId: IssueId, param: AddTaskToIssue.Param)(implicit
      ec: ExecutionContext
  ): IO[AddTaskToIssue.Error, TaskId] = ???

  def userExists(userId: UserId): IO[Nothing, Boolean] = ???

  def randomId = UUID.randomUUID().toString()
}
