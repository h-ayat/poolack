package dive.poolack.routes

import spray.json.DefaultJsonProtocol._
import spray.json._
import dive.poolack.Issue

object JsonSupport {
  

  implicit val issueFormat: RootJsonFormat[Issue] = jsonFormat2(Issue)

}
