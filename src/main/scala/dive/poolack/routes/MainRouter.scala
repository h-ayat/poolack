package dive.poolack.routes

import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import dive.poolack.Issue
import JsonSupport.issueFormat
import dive.poolack.api.IssueApi

object MainRouter {

  private val apiRoute: Route = pathPrefix("api" / "issues") {
    path("add") {
      post {
        entity(as[Issue]) { body =>
          val result = IssueApi.addIssue(body)
          onComplete(result) { size =>
            complete("Ok, dbSize: " + size)
          }
        }
      }
    }
  }

  private val docRouter: Route =
    pathPrefix("docs") {
      get {
        getFromResourceDirectory("docs")
      }
    } ~
      path("swagger") {
        get {
          getFromResource("swagger.html")
        }
      }

  val route: Route =
    apiRoute ~ docRouter

}
