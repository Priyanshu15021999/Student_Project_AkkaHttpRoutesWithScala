package Controlare

import Modal.StudentC
import org.mongodb.scala.Document

import scala.concurrent.Future

trait ServicesTrait {

  def insertStudent(studentC:StudentC): Future[String]
  def findOntStudent(id:Int) : Future[String]
  def updateStudent(id:Int,field:String,value:String): Future[String]
  def deleteStudent(id:Int): Future[String]
  def findAllStudent():Future[Seq[String]]
  def findTotalNumberOffStudent(): Future[Int]

  def findStudentDataBasedOnField(_id:Int,field:String):String
  def deleteAllStudentRecode():String
}
