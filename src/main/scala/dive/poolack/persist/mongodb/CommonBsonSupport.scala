package dive.poolack.persist.mongodb

import reactivemongo.api.bson.BSONHandler
import dive.poolack.api.UserId
import dive.poolack.api.IssueId


object CommonBsonSupport {

  implicit val userIdHandler: BSONHandler[UserId] = ???
  implicit val issueIdHandler: BSONHandler[IssueId] = ???
}
