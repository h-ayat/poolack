package dive.poolack.api

import dive.poolack.Issue
import dive.poolack.persist.memory.MemoryIssueRepo
import akka.compat.Future
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import dive.poolack.persist.api.IssueRepo

class IssueApi(repo: IssueRepo) {

  def addIssue(issue: Issue)(implicit ec: ExecutionContext): Future[Unit] = {
    repo.insert(issue)
  }
}
