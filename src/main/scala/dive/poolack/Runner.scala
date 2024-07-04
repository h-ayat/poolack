package dive.poolack

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import dive.poolack.routes.MainRouter
import dive.poolack.persist.mongodb.Connector
import akka.http.scaladsl.server.Route
import dive.poolack.persist.mongodb.MongoIssueRepo

object Runner {
  private val port = 8080
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("poolack-http-system")
    val bindingFuture = Http().newServerAt("0.0.0.0", port).bind(MainRouter.route)
    println(s"Server is   Running on port $port")
  }
}
