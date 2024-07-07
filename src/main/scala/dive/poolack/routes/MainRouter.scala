package dive.poolack.routes

import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json._

import dive.poolack.Issue
import dive.poolack.persist.mongodb.MongoIssueRepo
import dive.poolack.api.NewIssue
import dive.poolack.api.NewIssueJsonSupport

import NewIssue.Param
import dive.poolack.logic.IssueLogic
import scala.util.Success
import scala.util.Failure
import akka.http.scaladsl.model.StatusCodes
import org.slf4j.LoggerFactory
import dive.poolack.api.IssueId
import dive.poolack.util.IO
import akka.http.scaladsl.server.{RequestContext, RouteResult}
import scala.concurrent.Future

object MainRouter {

  private val log = LoggerFactory.getLogger(getClass)

  import NewIssueJsonSupport.{resultHandler, paramHandler}
  import NewIssueJsonSupport.ErrorSupport.errorFormat

  private val apiRoute: Route = pathPrefix("api" / "issues") {
    path("test") {

      log.warn("In call shod")
      complete("ok")
    } ~
    path("add") { //     POST /api/issues/add
      post {
        entity(as[Param]) { body =>
          ioComplete(body, IssueLogic.newIssue(body))
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

  private val tester = pathPrefix("test") {
    path("500") {
      throw new Exception("ok")
    } ~ path("400") {
      // complete(status
      complete(status = StatusCodes.BadRequest, "")
    } ~ path("307") {
      complete(status = StatusCodes.TemporaryRedirect, "")
    }
  }

  val route: Route =
    apiRoute ~ docRouter ~ tester

  private def ioComplete[P, E, A](
      param: P,
      io: IO[E, A]
  )(implicit eFormat: RootJsonFormat[E], aFormat: RootJsonFormat[A]): Route = {
    onComplete(io.future) {
      case Success(Right(value)) =>
        complete(value)

      case Success(Left(fail)) =>
        complete(status = StatusCodes.BadRequest, fail)

      case Failure(ex) =>
        ex.printStackTrace()
        log.error(s"Failed to process $param due to ", ex)
        complete(
          status = StatusCodes.InternalServerError,
          "Failed to execute"
        )
    }
  }

}
