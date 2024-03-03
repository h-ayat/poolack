package dive.poolack

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import dive.poolack.routes.MainRouter
import dive.poolack.persist.mongodb.Connector
import akka.http.scaladsl.server.Route
import dive.poolack.persist.mongodb.MongoIssueRepo

object Runner {

  private val connector = new Connector()
  def main(args: Array[String]): Unit = {

    val connector = new Connector()
    val repo = new MongoIssueRepo(connector)
    val mainRoutes: Route = new MainRouter().route
    implicit val system = ActorSystem("poolack-http-system")
    val bindingFuture = Http().newServerAt("0.0.0.0", 8080).bind(mainRoutes)
    println("Server Running")

  }
}
