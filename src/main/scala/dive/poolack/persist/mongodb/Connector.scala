package dive.poolack.persist.mongodb

import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.api.AsyncDriver
import reactivemongo.api.bson.collection.BSONCollection
import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import reactivemongo.api.DB
import reactivemongo.api.bson.BSONDocument

object Connector {

  private val db: DB = {
    println("Connecting")
    val driver = AsyncDriver()
    val connection = driver.connect("mongodb://127.0.0.1:27017/")
    Await.result(connection.flatMap(_.database("poolack")), 20.seconds)
  }

  val issues = getCol("issues")

  def getCol(name: String): BSONCollection = {
    db.collection(name)
  }

  def test(): Unit = {

    issues.insert(false).one(BSONDocument("name" -> "hasan"))
  }

}
