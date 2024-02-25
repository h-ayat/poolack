package dive.poolack.persist.memory

import dive.poolack.Issue
import scala.concurrent.Future
import scala.collection.mutable
import dive.poolack.persist.api.IssueRepo
import scala.concurrent.ExecutionContext

object MemoryIssueRepo extends IssueRepo {

  override def getOne(issueId: String)(implicit ec: ExecutionContext): Future[Option[Issue]] = ???


  private val data: mutable.HashMap[String, Issue] =
    mutable.HashMap[String, Issue]()

  def insert(issue: Issue)(implicit ec: ExecutionContext): Future[Unit] = {
    this.synchronized {
      data.put(issue.id, issue)
    }

    data.foreach(println)

    Future.successful(())
  }
}
