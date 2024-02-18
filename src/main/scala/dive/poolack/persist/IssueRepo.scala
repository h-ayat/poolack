package dive.poolack.persist

import dive.poolack.Issue
import scala.concurrent.Future
import scala.collection.mutable

object IssueRepo {

  private val data: mutable.HashMap[String, Issue] =
    mutable.HashMap[String, Issue]()

  def insertIssue(issue: Issue): Future[Int] = {
    this.synchronized {
      data.put(issue.id, issue)
    }

    data.foreach(println)

    Future.successful(data.size)
  }
}
