package dive.poolack.persist

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import reactivemongo.api.bson.BSONDocument
import dive.poolack.persist.mongodb.BsonUtil._
import dive.poolack.api.IssueId
import dive.poolack.api.LabelId
import dive.poolack.Issue
import scala.concurrent.Future

class BsonSpec extends AnyFlatSpec with Matchers {

  "charand" should "parand" in {
    // { _id: "folan"}
    val q_raw = BSONDocument(("_id", IssueId("folan").value))
    val q_short = idIs(IssueId("folan"))
    pretty(q_raw) shouldBe pretty(q_short)

    val model_raw = BSONDocument("model" -> IssueId("sedan").value)
    val model_short = "model" is IssueId("sedan")

    pretty(model_raw) shouldBe pretty(model_short)

    // model in List("", "" , "")
    val q2 =
      BSONDocument("model" -> BSONDocument("$in" -> List("sedan", "suv")))
    val q2_short = "model".in(List("sedan", "suv"))
    pretty(q2) shouldBe pretty(q2_short)

    val labls = List(LabelId("a"), LabelId("b"))
    "label".inIds(labls)

    // { year: { $gte:  1385 }  }
    BSONDocument("year" -> BSONDocument("$gte" -> 1385))
    // { year: { $lt:  1396 }  }
    BSONDocument("year" -> BSONDocument("$lt" -> 1396))

    // { year: { $gte : 1385 , $lt: 1396 }}
    val q3 = BSONDocument("year" -> BSONDocument("$gte" -> 1385, "$lt" -> 1396))
    val q3_short = "year".between(1385, 1396)

  }

  def findByLabels(labels: List[LabelId]): Future[List[Issue]] = {

    val filter = "label".inIds(labels)
    ???
  }

}

import dive.poolack.persist.mongodb.{Table, Column}

object Customers extends Table {

  object Id extends Column[String]
  object Name extends Column[String]
  object Age extends Column[Int]

  object Heigh extends Column[Int]

  def fields = List(Id, Name, Age)

}

object Cars extends Table {
  object Id extends Column[String]
  def fields = List(Id)
}

object Sandbox {
  import dive.poolack.persist.mongodb.SqlUtil._

  // val customers = new Customers()

  from(Customers) select (
    Customers.Id,
    Customers.Name
  ) where (Customers.Age === Customers.Heigh)
//    .where(Customers.Age === 12)

  // from (table) select (field1 , field2) where fieldx == fieldy ;

}

class Country {
  final case class Currency(value: Int) {
    def add(other: Currency): Currency =
      Currency(value + other.value)
  }

}

object Test {
  val iran = new Country()

  val myMoney1 = iran.Currency(3)
  val myMoney2 = iran.Currency(6)

  val myMoneySum = myMoney1 add myMoney2

  val usa = new Country()
  val navidsMoney = usa.Currency(3)

  // Compile error
  // val otherMony = myMoneySum add navidsMoney
}


