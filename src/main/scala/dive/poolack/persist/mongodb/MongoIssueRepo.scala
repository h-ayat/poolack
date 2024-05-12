package dive.poolack.persist.mongodb

import dive.poolack.Issue
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.bson.BSONDocument
import reactivemongo.api.bson.BSONDocumentHandler
import scala.util.Try
import reactivemongo.api.bson.Macros

object MongoIssueRepo {

  def getOne(
      issueId: String
  )(implicit ec: ExecutionContext): Future[Option[Issue]] = {
    val query = BSONDocument("id" -> issueId)
    col.find(query).one[Issue]
  }

  private val col = Connector.issues

  import CommonBsonSupport.{issueIdHandler, userIdHandler}
  private implicit val issueHandler: BSONDocumentHandler[Issue] =
    Macros.handler[Issue]

  def insert(
      issue: Issue
  )(implicit ec: ExecutionContext): Future[Unit] =
    col
      .insert(false)
      .one(issue)
      .map(_ => ())

}
