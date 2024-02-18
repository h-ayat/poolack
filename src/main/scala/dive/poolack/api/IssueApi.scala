package dive.poolack.api

import dive.poolack.Issue
import dive.poolack.persist.IssueRepo
import akka.compat.Future
import scala.concurrent.Future
import scala.concurrent.ExecutionContext

object IssueApi {
  
  def addIssue(issue: Issue)(implicit ec: ExecutionContext): Future[Int] = {
    IssueRepo.insertIssue(issue)
  }
}
