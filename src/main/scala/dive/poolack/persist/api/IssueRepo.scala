package dive.poolack.persist.api

import dive.poolack.Issue
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

trait IssueRepo {

  def insert(issue: Issue)(implicit ec: ExecutionContext): Future[Unit]

  def getOne(issueId: String)(implicit
      ec: ExecutionContext
  ): Future[Option[Issue]]
}
