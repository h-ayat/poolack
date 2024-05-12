package dive.poolack.api

import spray.json.JsonFormat
import spray.json._
import spray.json.DefaultJsonProtocol._
import dive.poolack.api.NewIssue.Error.StringValidation
import dive.poolack.api.NewIssue.Error.UserNotFound

object NewIssueJsonSupport {
  import CommonJsonSupport.formatUserId
  import CommonJsonSupport.formatIssueId

  implicit val paramHandler: RootJsonFormat[NewIssue.Param] =
    jsonFormat3(NewIssue.Param)

  implicit val resultHandler: RootJsonFormat[NewIssue.Result] = jsonFormat1(
    NewIssue.Result
  )

  object ErrorSupport {
    private object Formats {
      val stringValidation = jsonFormat1(
        NewIssue.Error.StringValidation
      )
      val userNotFound = jsonFormat1(NewIssue.Error.UserNotFound)
    }

    private object Name {
      val stringValidation = "StringValidation"
      val userNotFound = "UserNotFound"
    }

    implicit val errorFormat: RootJsonFormat[NewIssue.Error] =
      new RootJsonFormat[NewIssue.Error] {
        override def read(json: JsValue): NewIssue.Error = {
          json.asJsObject.fields(CommonJsonSupport.tag) match {
            case JsString(value) =>
              value match {
                case Name.stringValidation =>
                  Formats.stringValidation.read(json)
                case Name.userNotFound =>
                  Formats.userNotFound.read(json)
                case _ =>
                  ???
              }
            case _ =>
              ???
          }
        }

        override def write(obj: NewIssue.Error): JsValue = {
          val (name, js) = obj match {
            case o: StringValidation =>
              Name.stringValidation -> Formats.stringValidation.write(o)

            case o: UserNotFound =>
              (Name.userNotFound, Formats.userNotFound.write(o))
          }

          val fields =
            js.asJsObject.fields + (CommonJsonSupport.tag -> JsString(name))
          new JsObject(fields)
        }

      }
  }
}

object CommonJsonSupport {
  val tag = "_TAG"
  def idFormat[T <: Id](constructor: String => T) =
    new JsonFormat[T] {
      override def read(json: JsValue): T =
        json match {
          case JsString(value) => constructor(value)
          case _ =>
            throw new Exception("Expected simple string but got " + json)
        }

      override def write(obj: T): JsValue = JsString(obj.value)
    }

  implicit val formatUserId: JsonFormat[UserId] =
    idFormat[UserId](UserId.apply)
  implicit val formatLabelId: JsonFormat[LabelId] =
    idFormat[LabelId](LabelId.apply)
  implicit val formatTaskId: JsonFormat[TaskId] = idFormat[TaskId](TaskId.apply)
  implicit val formatIssueId: JsonFormat[IssueId] =
    idFormat[IssueId](IssueId.apply)

}
