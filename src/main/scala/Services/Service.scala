package Services

import Controlare.ServicesTrait
import Modal.StudentC
import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Projections
import org.mongodb.scala.model.Updates.set
import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, Macros}

import scala.concurrent.{Await, Future}

//case class StudentC(_id:Int, firstname:String, lastname:String, Class:Int, country:String, subject:String, sSubjects:Array[String])

import scala.language.postfixOps
import org.mongodb.scala.{Document, MongoClient, MongoCollection, bson}
import org.mongodb.scala.bson.{BsonArray, BsonDocument, BsonDouble, BsonInt32, BsonString, BsonValue}
import org.mongodb.scala.model.{Filters, Updates}
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import Modal.StudentC
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros, document}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.bson.BSONObject
import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{AsyncDriver, Cursor, DB, MongoConnection}

import spray.json.DefaultJsonProtocol.immSeqFormat
import spray.json.{DefaultJsonProtocol, JsonWriter, enrichAny}
import scala.concurrent.ExecutionContext.Implicits.global


class Service extends ServicesTrait {
  val sudentURL="mongodb://localhost:27017"
  val studentClint=MongoClient(sudentURL)
  val student_Database=studentClint.getDatabase("studentDatabase")
  val student_Collection=student_Database.getCollection("studentCollection")
  implicit def personWriter: BSONDocumentWriter[StudentC] = Macros.writer[StudentC]
  implicit def personReader: BSONDocumentReader[StudentC] = Macros.reader[StudentC]

  override def insertStudent(studentC: StudentC): Future[String] = Future{
   val filter= BsonDocument(
     "_id"->studentC._id,
     "firstname"->studentC.firstname,
     "lastname"->studentC.lastname,
     "Class"->studentC.Class,
     "subject"->studentC.subject,
     "country"->studentC.country,
     "Array" -> studentC.sSubjects.toList
   )
    val result1 = student_Collection.insertOne(filter)
    val s = Await.result(result1.toFuture(), 100 seconds)
    "Student is Stored"
  }


  override def findOntStudent(id: Int): Future[String] = Future{
    val filter = Filters.eq("_id", id)
    val result1 = student_Collection.find(filter)
    val s = Await.result(result1.toFuture(), 100 seconds)
    s.map(a=>a.toJson()).toString()
  }

  override def updateStudent(id: Int, field: String, value: String): Future[String] = {
println("=============================================" + field)
    val filter = Filters.eq("_id", id)
    val update = set(field,  if( field == "Class") value.toInt else value )
    val result = student_Collection.updateOne(filter, update).toFuture()

    result.map { updateResult =>
      if (updateResult.getModifiedCount == 1) {
        s"Successfully updated student with ID $id"
      } else {
        s"Failed to update student with ID $id"
      }
    }
  }


  override def deleteStudent(id: Int): Future[String] = Future{
    val filter = Filters.eq("_id", id)
    val result1 = student_Collection.deleteOne(filter)
    val s = Await.result(result1.toFuture(), 100 seconds)
    "delete Student Successfully"
  }


  override def findAllStudent(): Future[Seq[String]] = Future{
    val result1 = student_Collection.find()
    val s = Await.result(result1.toFuture(), 100 seconds)
    s.map(a=>a.toJson()+"\n")
  }

  override def findTotalNumberOffStudent(): Future[Int] = Future{
    val result1 = student_Collection.find()
    val s = Await.result(result1.toFuture(), 100 seconds)
    s.length
  }


  override def findStudentDataBasedOnField(id: Int, field: String): String = {
    val filter = Filters.eq("_id", id)
    val projection = Projections.fields(Projections.include(field), Projections.excludeId())
    val result1 = student_Collection.find(filter).projection(projection).first()
    val s = Await.result(result1.toFuture(), 100 seconds)

    s.toJson()
  }

  def findByClassAndFirstname(Class: Int, firstname: String): String = {
    val filter = Filters.and(
      Filters.eq("Class", Class),
      Filters.eq("firstname", firstname)
    )
    val result1 = student_Collection.find(filter).first()
    val s = Await.result(result1.toFuture(), 100 seconds)
    s.toJson()
  }

override def deleteAllStudentRecode(): String = {
  val result1 = student_Collection.deleteMany(Document())
  Await.result(result1.toFuture(), 100 seconds)
  "all data deleted from database"
}
}
