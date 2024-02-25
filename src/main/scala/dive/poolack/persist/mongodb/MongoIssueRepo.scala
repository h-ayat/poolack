package dive.poolack.persist.mongodb

import dive.poolack.persist.api.IssueRepo
import dive.poolack.Issue
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.BSONDocumentHandler
import scala.util.Try
import reactivemongo.api.bson.Macros

class MongoIssueRepo(connector: Connector) extends IssueRepo {

  override def getOne(
      issueId: String
  )(implicit ec: ExecutionContext): Future[Option[Issue]] = {
    val query = BSONDocument("id" -> issueId)
    col.find(query).one[Issue]
  }

  private val col = connector.issues

  private implicit val issueHandler: BSONDocumentHandler[Issue] =
    Macros.handler[Issue]
  override def insert(
      issue: Issue
  )(implicit ec: ExecutionContext): Future[Unit] =
    col
      .insert(false)
      .one(issue)
      .map(_ => ())

}
