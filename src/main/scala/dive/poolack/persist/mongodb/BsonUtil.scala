package dive.poolack.persist.mongodb

import reactivemongo.api.bson.BSONDocument
import dive.poolack.api.Id

object BsonUtil {

  def idIs(value: String): BSONDocument =
    is("_id", value)

  def idIs(id: Id): BSONDocument =
    is("_id", id.value)

  def is(field: String, id: Id): BSONDocument =
    BSONDocument(field -> id.value)
  def is(field: String, value: String): BSONDocument =
    BSONDocument(field -> value)
  def is(field: String, value: Int): BSONDocument =
    BSONDocument(field -> value)
  def is(field: String, value: Long): BSONDocument =
    BSONDocument(field -> value)

  def pretty(doc: BSONDocument): String =
    BSONDocument.pretty(doc).replace("\n", "")

  implicit class FieldNameWrapper(name: String) {
    def is(value: String): BSONDocument = BsonUtil.is(name, value)
    def is(id: Id): BSONDocument = BsonUtil.is(name, id)

    def inIds(values: Seq[Id]): BSONDocument =
      BSONDocument(name -> BSONDocument("$in" -> values.map(_.value)))

    def inIds(values: Set[Id]): BSONDocument =
      BSONDocument(name -> BSONDocument("$in" -> values.map(_.value)))

    def in(values: Seq[String]): BSONDocument =
      BSONDocument(name -> BSONDocument("$in" -> values))

    def in(values: Set[String]): BSONDocument =
      BSONDocument(name -> BSONDocument("$in" -> values))

    def gte(in: Int): BSONDocument =
      BSONDocument(name -> BSONDocument("$gte" -> in))

    def gt(in: Int): BSONDocument =
      BSONDocument(name -> BSONDocument("$gt" -> in))

    def lt(in: Int): BSONDocument =
      BSONDocument(name -> BSONDocument("$lt" -> in))
    def lte(in: Int): BSONDocument =
      BSONDocument(name -> BSONDocument("$lte" -> in))

    def between(gte: Int, lt: Int): BSONDocument =
      BSONDocument(name -> BSONDocument("$gte" -> gte, "$lte" -> lt))

  }

}

object SqlUtil {

  def from(table: Table): SelectBuilder =
    new SelectBuilder(table)

  class SelectBuilder(table: Table) {
    def select(fields: Column[_]*): WhereBuilder = ???
  }

  class WhereBuilder(table: Table, fields: List[Column[_]]) {
    def where(condition: Condition): Query = ???
  }

  trait Query

  sealed trait Condition

  implicit class ConditionMaker[T](field: Column[T]) {

    def ===(t: T): Condition = ???

    def ===(other: Column[T]): Condition = ???
  }
}

trait Column[T]

trait Table {
  def fields: List[Column[_]]
}
