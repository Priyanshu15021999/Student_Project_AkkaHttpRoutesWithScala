package Controlare
//import Controlare.Main.{bindingFuture, mongoClient}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{entity, path, _}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol

import scala.util.{Failure, Success, Try}
import Modal.StudentC
import akka.http.scaladsl.model.StatusCodes

import scala.io.Source.{fromFile, fromInputStream}
import scala.concurrent.Future
import akka.http.scaladsl.Http
import akka.stream.scaladsl.Source
import akka.util.ByteString

import scala.io.Source.{fromFile, fromInputStream}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{RejectionHandler, Route, ValidationRejection}
import com.sun.deploy.services.Service

import scala.concurrent.Future
import scala.io.StdIn


case class k(name:String)
case class S(s:k)
trait StudentJson extends SprayJsonSupport with DefaultJsonProtocol{
  import spray.json._
  implicit val printar= PrettyPrinter
  implicit val  JsonStudetFormat=jsonFormat7(StudentC)
  implicit val  JsonStudetFormat1= jsonFormat1(k)
  implicit val  JsonStudetFormat2= jsonFormat1(S)

}
import scala.concurrent.ExecutionContext.Implicits.global
class Routes extends  StudentJson  {
val Service=new Services.Service()
  val find= path("Find"/IntNumber) { id =>
    get {
      //val studentFind= Service.findOntStudent(id)
      onSuccess(Service.findOntStudent(id)) { donuts =>
        complete(StatusCodes.OK, donuts)
      }
    } }~ path("Insert") {
      post {
        entity(as[StudentC]) { student =>

          val studentFind = Service.insertStudent(student)
          onComplete(studentFind) {
              case Success(value) => complete(value)

          }
        }

      }} ~ path("Update" / IntNumber) { id =>
        put {
          entity(as[StudentC]) { student =>
            //val studentFind = Service.updateStudent(id, "StudentName", student.sName)
            onSuccess(Service.updateStudent(id, "StudentName", student.firstname)) { donuts =>
              complete(StatusCodes.OK, donuts)
            }
          }
        }

      } ~ path("Delete" / IntNumber) { id =>
        delete {
          val studentFind = Service.deleteStudent(id)
          onSuccess(Service.deleteStudent(id)) { donuts =>
            complete(StatusCodes.OK, donuts)
          }
        }
  }
}


class htmlRouts  extends  StudentJson{
  val Service=new Services.Service()
/*
//==============================================================================================================================================


  val find = path("InsertStudent") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        s"""
                   <!DOCTYPE html>
           |<html>
           |<head>
           |    <meta name="viewport" content="width=device-width, initial-scale=1">
           |    <style>
           |body {font-family: Arial, Helvetica, sans-serif;}
           |* {box-sizing: border-box;}
           |
           |input[type=text], select, textarea {
           |  width: 100%;
           |  padding: 12px;
           |  border: 1px solid #ccc;
           |  border-radius: 4px;
           |  box-sizing: border-box;
           |  margin-top: 6px;
           |  margin-bottom: 16px;
           |  resize: vertical;
           |}
           |
           |input[type=submit] {
           |  background-color: #04AA6D;
           |  color: white;
           |  padding: 12px 20px;
           |  border: none;
           |  border-radius: 4px;
           |  cursor: pointer;
           |}
           |
           |input[type=submit]:hover {
           |  background-color: #45a049;
           |}
           |
           |.container {
           |  border-radius: 5px;
           |  background-color: #f2f2f2;
           |  padding: 20px;
           |}
           |</style>
           |</head>
           |<body>
           |
           |<center><h3>===== Insert Student ===</h3></center>
           |
           |<div class="container">
           |   <form action="/action_page.php", method="post">
           |
           |        <label for="_id">_id</label>
           |        <input type="number" id="lname" name="_id" placeholder="Your _id.">
           |<br><br>
           |        <label for="fname">First Name</label>
           |        <input type="text" id="fname" name="firstname" placeholder="Your name..">
           |
           |        <label for="lname">Last Name</label>
           |        <input type="text" id="lname" name="lastname" placeholder="Your last name..">
           |
           |        <label for="class">Class</label>
           |        <input type="number" id="lname" name="Class" placeholder="Your Class.">
           |        <br><br>
           |   <div>
           |    <br>
           |       <label for="" >Select our Subjects : </label>
           |        <label for="English">English</label><input type="radio" name="English" class="" value="English">
           |        <label for="Math">Math</label><input type="radio" name="Math" class="" value="Math">
           |        <label for="SosalScience">SosalScience</label><input type="radio" name="SosalScience" class="" value="SosalScience">
           |        <label for="Science">Science</label><input type="radio" name="Science" class="" value="Science">
           |       <br><br>
           |        <label for="" >Select any one Subject: </label>
           |        <label for="Sanskrat">Sanskrat</label><input type="radio" name="Hindi" class="" value="Sanskrat">
           |        <label for="Hindi">Hindi</label><input type="radio" name="Hindi" class="" value="Hindi">  /// English,Math, SosalScience,Science,Sanskrat,Hindi,
           |     <br>
           |      </div>
           |
           |        <label for="country">Country</label>
           |        <select id="country" name="country">
           |            <option value="india">India</option>
           |            <option value="australia">Australia</option>
           |            <option value="canada">Canada</option>
           |            <option value="usa">USA</option>
           |         </select>
           |
           |        <label for="subject">Subject</label>
           |        <textarea id="subject" name="subject" placeholder="Write something.." style="height:200px"></textarea>
           |
           |        <input type="submit" value="Submit">
           |    </form>
           |</div>
           |
           |</body>
           |</html>
           |""".stripMargin))
    }
    } ~ path("action_page.php") {
  post {
    formFields('_id,'firstname, 'lastname,'Class ,'English,'Math,'SosalScience,'Science,'Hindi,'country, 'subject ) { (_id,firstname, lastname, Class,English,Math, SosalScience,Science,Hindi, country, subject) =>
 val student=StudentC(_id.toInt,firstname,lastname,Class.toInt,country,subject,Array(English,Math, SosalScience,Science,Hindi))
      val studentFind = Service.insertStudent(student)
      onComplete(studentFind) {
        case Success(value) => complete(value)
        case Failure(exception) => complete("========"+exception)
      }
    }
  }
}
//===========================================================================================================================================


  val find1 = path("deleteStudentGet") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        s"""
           |<!DOCTYPE html>
           |<html>
           |<head>
           |    <meta name="viewport" content="width=device-width, initial-scale=1">
           |    <style>
           |body {font-family: Arial, Helvetica, sans-serif;}
           |* {box-sizing: border-box;}
           |
           |input[type=text], select, textarea {
           |  width: 100%;
           |  padding: 12px;
           |  border: 1px solid #ccc;
           |  border-radius: 4px;
           |  box-sizing: border-box;
           |  margin-top: 6px;
           |  margin-bottom: 16px;
           |  resize: vertical;
           |}
           |
           |
           |input[type=submit] {
           |  background-color: #04AA6D;
           |  color: white;
           |  padding: 12px 20px;
           |  border: none;
           |  border-radius: 4px;
           |  cursor: pointer;
           |}
           |input[type=submit]:hover {
           |  background-color: #45a049;
           |}
           |
           |.container {
           |  border-radius: 5px;
           |  background-color: #f2f2f2;
           | padding: 20px;
           |}
           |</style>
           |</head>
           |<body>
           |
           |<center class="center"><h2 id="h">=====> Delete Student <====</h2>
           |
           |<div class="container">
           |    <form action="/action_page.php1", method="post">
           |
           |        <label for="_id">Student_id : </label>
           |        <input type="number" id="lname" name="_id" placeholder="Your Student_id.">
           |<br><br>
           |
           |        <input type="submit" value="Submit">
           |    </form>
           |</div></center>
           |
           |</body>
           |</html>
           |
           |""".stripMargin))
    }
  } ~ path("action_page.php1") {
    post {
      formFields('_id ) { (_id) =>

        val studentdelete = Service.deleteStudent(_id.toInt)
        onComplete(studentdelete) {
          case Success(value) => complete(value)
          case Failure(exception) => complete("========" + exception)
        }
      }
    }
  }
///===============================================================================================================================================



  val find2 = path("findStudent") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        s"""
                    <!DOCTYPE html>
           |<html>
           |<head>
           |    <meta name="viewport" content="width=device-width, initial-scale=1">
           |    <style>
           |body {font-family: Arial, Helvetica, sans-serif;}
           |* {box-sizing: border-box;}
           |
           |input[type=text], select, textarea {
           |  width: 100%;
           |  padding: 12px;
           |  border: 1px solid #ccc;
           |  border-radius: 4px;
           |  box-sizing: border-box;
           |  margin-top: 6px;
           |  margin-bottom: 16px;
           |  resize: vertical;
           |}
           |
           |input[type=submit] {
           |  background-color: #04AA6D;
           |  color: white;
           |  padding: 12px 20px;
           |  border: none;
           |  border-radius: 4px;
           |  cursor: pointer;
           |}
           |
           |input[type=submit]:hover {
           |  background-color: #45a049;
           |}
           |
           |.container {
           |  border-radius: 5px;
           |  background-color: #f2f2f2;
           |  padding: 20px;
           |}
           |</style>
           |</head>
           |<body>
           |
           |<center class="center"><h2 id="h">=====> Find One Student <====</h2>
           |
           |<div class="container">
           |   <form action="/action_page.php", method="get">
           |
           |       <label for="_id">Student_id</label>
           |           |        <input type="number" id="lname" name="_id" placeholder="ZYour Sudent_id.">
           |                       <input type="submit" value="Submit">
           |    </form>
           |</div>
           |
           |</body>
           |</html>
           |""".stripMargin))
    }
  } ~ path("action_page.php") {
      get {
        parameter('_id.as[Int]) { (_id) =>
          val studentfind = Service.findOntStudent(_id)
         // Thread.sleep(4000)
                 println(studentfind+"?????????????????????????")
          onComplete(studentfind) {
            case Success(value) => complete(value.toString())
            case Failure(exception) => complete("========" + exception)
          }
        }
      }
    }

  //=======================================================================================================================================================


  val find3 = path("UpdateStudent") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        s"""
           |<!DOCTYPE html>
           |<html>
           |<head>
           |    <meta name="viewport" content="width=device-width, initial-scale=1">
           |    <style>
           |body {font-family: Arial, Helvetica, sans-serif;}
           |* {box-sizing: border-box;}
           |
           |input[type=text], select, textarea {
           |  width: 100%;
           |  padding: 12px;
           |  border: 1px solid #ccc;
           |  border-radius: 4px;
           |  box-sizing: border-box;
           |  margin-top: 6px;
           |  margin-bottom: 16px;
           |  resize: vertical;
           |}
           |
           |input[type=submit] {
           |  background-color: #04AA6D;
           |  color: white;
           |  padding: 12px 20px;
           |  border: none;
           |  border-radius: 4px;
           |  cursor: pointer;
           |}
           |input[type=submit]:hover {
           |  background-color: #45a049;
           |}
           |
           |.container {
           |  border-radius: 5px;
           |  background-color: #f2f2f2;
           | padding: 20px;
           |}
           |</style>
           |</head>
           |<body>
           |
           |<center><h3>===== Update One Student Data Field ===</h3>
           |
           |<div class="container">
           |    <form action="/update", method="post">
           |
           |        <label for="_id">Student_id</label>
           |        <input type="number" id="lname" name="_id" placeholder="ZYour Sudent_id.">
           |<br><br>
           |<br>
           |//<!-- (_id.toInt,firstname,lastname,Class.toInt,country,subject,Array(English,Math, SosalScience,Science,Hindi)) -->
           |<div>
           | <label for="" >Select Any One Student  Field which you want to Update: </label><br><br>
           |  <label for="firstname">firstname</label><input type="radio" name="field" class="" value="firstname">||
           |
           |  <label for="lastname">lastname</label><input type="radio" name="field" class="" value="lastname">||
           |
           |  <label for="Class">Class</label><input type="radio" name="field" class="" value="Class">  ||
           |
           |  <label for="country">country</label><input type="radio" name="field" class="" value="country">||
           |
           |  <label for="subject">subject</label><input type="radio" name="field" class="" value="subject">
           |
           |</div>
           | <br>
           |        <label for="Value">Update Value</label>
           |        <input type="text" id="lname" name="value" placeholder="Your last name.." class="">
           |
           |        <input type="submit" value="Submit">
           |    </form></center>
           |</div>
           |
           |</body>
           |</html>
           |""".stripMargin))
    }
  } ~ path("update") {
    post {
   formFields('_id.as[Int], 'field.as[String],'value.as[String]) { (_id, field, value) =>
           println( "??????????????????????????????????????????????"+field )
          println(  field.toString =="Class")
     println("")
        val studentfind = Service.updateStudent(_id, field, value)
        onComplete(studentfind) {
          case Success(value) => complete(value)
          case Failure(exception) => complete("========" + exception)
        }
      }
    }
  }

  val sss=path("akka-http-getresource") {
  getFromResource("View\\B.html")
} ~ path("functions.html") {
    parameter('method.as[String]) { (method) =>
      if(method=="find"){
        getFromResource("View\\B.html")
      }
      else if(method == "update") {
        getFromResource("View\\B.html")
      }
      else if (method == "delete") {
        getFromResource("View\\B.html")

      }else { getFromResource("View\\B.html")}


    }
  }*/


  val sss11 = path("ok") {
    getFromResource("View\\index.html")
  } ~ path("InsertStudent") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
          s"""
                      <!DOCTYPE html>
             |<html>
             |<head>
             |    <meta name="viewport" content="width=device-width, initial-scale=1">
             |    <style>
             |body {font-family: Arial, Helvetica, sans-serif;}
             |* {box-sizing: border-box;}
             |
             |input[type=text], select, textarea {
             |  width: 100%;
             |  padding: 12px;
             |  border: 1px solid #ccc;
             |  border-radius: 4px;
             |  box-sizing: border-box;
             |  margin-top: 6px;
             |  margin-bottom: 16px;
             |  resize: vertical;
             |}
             |
             |input[type=submit] {
             |  background-color: #04AA6D;
             |  color: white;
             |  padding: 12px 20px;
             |  border: none;
             |  border-radius: 4px;
             |  cursor: pointer;
             |}
             |
             |input[type=submit]:hover {
             |  background-color: #45a049;
             |}
             |
             |.container {
             |  border-radius: 5px;
             |  background-color: #f2f2f2;
             |  padding: 20px;
             |}
             |</style>
             |</head>
             |<body>
             |
             |<center><h3>===== Insert Student ===</h3></center>
             |
             |<div class="container">
             |   <form action="/action_page.php", method="post">
             |
             |        <label for="_id">_id</label>
             |        <input type="number" id="lname" name="_id" placeholder="Your _id.">
             |<br><br>
             |        <label for="fname">First Name</label>
             |        <input type="text" id="fname" name="firstname" placeholder="Your name..">
             |
             |        <label for="lname">Last Name</label>
             |        <input type="text" id="lname" name="lastname" placeholder="Your last name..">
             |
             |        <label for="class">Class</label>
             |        <input type="number" id="lname" name="Class" placeholder="Your Class.">
             |        <br><br>
             |   <div>
             |    <br>
             |       <label for="" >Select our Subjects : </label>
             |        <label for="English">English</label><input type="radio" name="English" class="" value="English">
             |        <label for="Math">Math</label><input type="radio" name="Math" class="" value="Math">
             |        <label for="SosalScience">SosalScience</label><input type="radio" name="SosalScience" class="" value="SosalScience">
             |        <label for="Science">Science</label><input type="radio" name="Science" class="" value="Science">
             |       <br><br>
             |        <label for="" >Select any one Subject: </label>
             |        <label for="Sanskrat">Sanskrat</label><input type="radio" name="Hindi" class="" value="Sanskrat">
             |        <label for="Hindi">Hindi</label><input type="radio" name="Hindi" class="" value="Hindi">  /// English,Math, SosalScience,Science,Sanskrat,Hindi,
             |     <br>
             |      </div>
             |
             |        <label for="country">Country</label>
             |        <select id="country" name="country">
             |            <option value="india">India</option>
             |            <option value="australia">Australia</option>
             |            <option value="canada">Canada</option>
             |            <option value="usa">USA</option>
             |         </select>
             |
             |        <label for="subject">Subject</label>
             |        <textarea id="subject" name="subject" placeholder="Write something.." style="height:200px"></textarea>
             |
             |        <input type="submit" value="Submit">
             |    </form>
             |</div>
             |
             |</body>
             |</html>
             |""".stripMargin))
      }
    } ~ path("action_page.php") {
      post {
        formFields('_id, 'firstname, 'lastname, 'Class, 'English, 'Math, 'SosalScience, 'Science, 'Hindi, 'country, 'subject) { (_id, firstname, lastname, Class, English, Math, SosalScience, Science, Hindi, country, subject) =>
          val student = StudentC(_id.toInt, firstname, lastname, Class.toInt, country, subject, Array(English, Math, SosalScience, Science, Hindi))
          val studentFind = Service.insertStudent(student)
          onComplete(studentFind) {
            case Success(value) => complete(value)
            case Failure(exception) => complete("========" + exception)
          }
        }
      }
   } ~ path("findStudent") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        s"""
                       <!DOCTYPE html>
           |<html>
           |<head>
           |    <meta name="viewport" content="width=device-width, initial-scale=1">
           |    <style>
           |body {font-family: Arial, Helvetica, sans-serif;}
           |* {box-sizing: border-box;}
           |
           |input[type=text], select, textarea {
           |  width: 100%;
           |  padding: 12px;
           |  border: 1px solid #ccc;
           |  border-radius: 4px;
           |  box-sizing: border-box;
           |  margin-top: 6px;
           |  margin-bottom: 16px;
           |  resize: vertical;
           |}
           |
           |input[type=submit] {
           |  background-color: #04AA6D;
           |  color: white;
           |  padding: 12px 20px;
           |  border: none;
           |  border-radius: 4px;
           |  cursor: pointer;
           |}
           |
           |input[type=submit]:hover {
           |  background-color: #45a049;
           |}
           |
           |.container {
           |  border-radius: 5px;
           |  background-color: #f2f2f2;
           |  padding: 20px;
           |}
           |</style>
           |</head>
           |<body>
           |
           |<center class="center"><h2 id="h">=====> Find One Student <====</h2>
           |
           |<div class="container">
           |   <form action="/action_page.php", method="get">
           |
           |       <label for="_id">Student_id</label>
           |           |        <input type="number" id="lname" name="_id" placeholder="ZYour Sudent_id.">
           |                       <input type="submit" value="Submit">
           |    </form>
           |</div>
           |
           |</body>
           |</html>
           |""".stripMargin))
    }
  } ~ path("action_page.php") {
    get {
      parameter('_id.as[Int]) { (_id) =>
        val studentfind = Service.findOntStudent(_id)
        // Thread.sleep(4000)
        println(studentfind + "?????????????????????????")
        onComplete(studentfind) {
          case Success(value) => complete(value.toString())
          case Failure(exception) => complete("========" + exception)
        }
      }
    }
  }  ~ path("UpdateStudent") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        s"""
           |<!DOCTYPE html>
           |<html>
           |<head>
           |    <meta name="viewport" content="width=device-width, initial-scale=1">
           |    <style>
           |body {font-family: Arial, Helvetica, sans-serif;}
           |* {box-sizing: border-box;}
           |
           |input[type=text], select, textarea {
           |  width: 100%;
           |  padding: 12px;
           |  border: 1px solid #ccc;
           |  border-radius: 4px;
           |  box-sizing: border-box;
           |  margin-top: 6px;
           |  margin-bottom: 16px;
           |  resize: vertical;
           |}
           |
           |input[type=submit] {
           |  background-color: #04AA6D;
           |  color: white;
           |  padding: 12px 20px;
           |  border: none;
           |  border-radius: 4px;
           |  cursor: pointer;
           |}
           |input[type=submit]:hover {
           |  background-color: #45a049;
           |}
           |
           |.container {
           |  border-radius: 5px;
           |  background-color: #f2f2f2;
           | padding: 20px;
           |}
           |</style>
           |</head>
           |<body>
           |
           |<center><h3>===== Update One Student Data Field ===</h3>
           |
           |<div class="container">
           |    <form action="/update", method="post">
           |
           |        <label for="_id">Student_id</label>
           |        <input type="number" id="lname" name="_id" placeholder="ZYour Sudent_id.">
           |<br><br>
           |<br>
           |//<!-- (_id.toInt,firstname,lastname,Class.toInt,country,subject,Array(English,Math, SosalScience,Science,Hindi)) -->
           |<div>
           | <label for="" >Select Any One Student  Field which you want to Update: </label><br><br>
           |  <label for="firstname">firstname</label><input type="radio" name="field" class="" value="firstname">||
           |
           |  <label for="lastname">lastname</label><input type="radio" name="field" class="" value="lastname">||
           |
           |  <label for="Class">Class</label><input type="radio" name="field" class="" value="Class">  ||
           |
           |  <label for="country">country</label><input type="radio" name="field" class="" value="country">||
           |
           |  <label for="subject">subject</label><input type="radio" name="field" class="" value="subject">
           |
           |</div>
           | <br>
           |        <label for="Value">Update Value</label>
           |        <input type="text" id="lname" name="value" placeholder="Your last name.." class="">
           |
           |        <input type="submit" value="Submit">
           |    </form></center>
           |</div>
           |
           |</body>
           |</html>
           |""".stripMargin))
    }
  } ~ path("update") {
    post {
      formFields('_id.as[Int], 'field.as[String], 'value.as[String]) { (_id, field, value) =>
        println("??????????????????????????????????????????????" + field)
        println(field.toString == "Class")
        println("")
        val studentfind = Service.updateStudent(_id, field, value)
        onComplete(studentfind) {
          case Success(value) => complete(value)
          case Failure(exception) => complete("========" + exception)
        }
      }
    }
  }~ path("deleteStudentGet") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        s"""
           |<!DOCTYPE html>
           |<html>
           |<head>
           |    <meta name="viewport" content="width=device-width, initial-scale=1">
           |    <style>
           |body {font-family: Arial, Helvetica, sans-serif;}
           |* {box-sizing: border-box;}
           |
           |input[type=text], select, textarea {
           |  width: 100%;
           |  padding: 12px;
           |  border: 1px solid #ccc;
           |  border-radius: 4px;
           |  box-sizing: border-box;
           |  margin-top: 6px;
           |  margin-bottom: 16px;
           |  resize: vertical;
           |}
           |
           |
           |input[type=submit] {
           |  background-color: #04AA6D;
           |  color: white;
           |  padding: 12px 20px;
           |  border: none;
           |  border-radius: 4px;
           |  cursor: pointer;
           |}
           |input[type=submit]:hover {
           |  background-color: #45a049;
           |}
           |
           |.container {
           |  border-radius: 5px;
           |  background-color: #f2f2f2;
           | padding: 20px;
           |}
           |</style>
           |</head>
           |<body>
           |
           |<center class="center"><h2 id="h">=====> Delete Student <====</h2>
           |
           |<div class="container">
           |    <form action="/action_page.php1", method="post">
           |
           |        <label for="_id">Student_id : </label>
           |        <input type="number" id="lname" name="_id" placeholder="Your Student_id.">
           |<br><br>
           |
           |        <input type="submit" value="Submit">
           |    </form>
           |</div></center>
           |
           |</body>
           |</html>
           |
           |""".stripMargin))
    }
  } ~ path("action_page.php1") {
    post {
      formFields('_id) { (_id) =>

        val studentdelete = Service.deleteStudent(_id.toInt)
        onComplete(studentdelete) {
          case Success(value) => complete(value)
          case Failure(exception) => complete("========" + exception)
        }
      }
    }
  }~ path("totalStudent"){
    get{
      val s= Service.findTotalNumberOffStudent()
      onComplete(s) {
        case Success(value) => complete(s" totalStudent in database  == ${value} ")
        case Failure(exception) => complete("========" + exception)
    }
  }}~ path("findAllStudent") {
    get {
      val s =   Service.findAllStudent()
      onComplete(s) {
        case Success(value) => complete(s"findAllStudent == ${value.map(a=>a+"\n")}")
        case Failure(exception) => complete("========" + exception)
    }
}} ~  path("findStudentDataBasedOnFieldd") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        s"""
           |<!DOCTYPE html>
           |<html>
           |<head>
           |    <meta name="viewport" content="width=device-width, initial-scale=1">
           |    <style>
           |body {font-family: Arial, Helvetica, sans-serif;}
           |* {box-sizing: border-box;}
           |
           |input[type=text], select, textarea {
           |  width: 100%;
           |  padding: 12px;
           |  border: 1px solid #ccc;
           |  border-radius: 4px;
           |  box-sizing: border-box;
           |  margin-top: 6px;
           |  margin-bottom: 16px;
           |  resize: vertical;
           |}
           |
           |
           |input[type=submit] {
           |  background-color: #04AA6D;
           |  color: white;
           |  padding: 12px 20px;
           |  border: none;
           |  border-radius: 4px;
           |  cursor: pointer;
           |}
           |input[type=submit]:hover {
           |  background-color: #45a049;
           |}
           |
           |.container {
           |  border-radius: 5px;
           |  background-color: #f2f2f2;
           | padding: 20px;
           |}
           |</style>
           |</head>
           |<body>
           |
           |<center class="center"><h2 id="h">=====> hhhhhhhhhhhhhhhhhh <====</h2>
           |
           |<div class="container">
           |    <form action="/findStudentDataBasedOnField", method="get">
           |
           |        <label for="_id">Student_id : </label>
           |        <input type="number" id="lname" name="_id" placeholder="Your Student_id.">
           |      <br><br>
           |        <div>
           | <label for="" >Select Any One Student  Field which you want to Update: </label><br><br>
           |  <label for="firstname">firstname</label><input type="radio" name="field" class="" value="firstname">||
           |
           |  <label for="lastname">lastname</label><input type="radio" name="field" class="" value="lastname">||
           |
           |  <label for="Class">Class</label><input type="radio" name="field" class="" value="Class">  ||
           |
           |  <label for="country">country</label><input type="radio" name="field" class="" value="country">||
           |
           |  <label for="subject">subject</label><input type="radio" name="field" class="" value="subject">
           |
           |</div>
           |<br><br>
           |
           |        <input type="submit" value="Submit">
           |    </form>
           |</div></center>
           |
           |</body>
           |</html>
           |
           |""".stripMargin))
    }} ~   path("findStudentDataBasedOnField"){
    get{
      parameters ('_id.as[Int],'field.as[String]){(_id, field)=>
      val s = Service.findStudentDataBasedOnField( _id:Int , field:String)
   complete(s)
    }}} ~ path("deleteAllStudentRecode") {
    get {
      val s = Service.deleteAllStudentRecode()
        complete(s)
      }
    }~ path("findByClassAndFirstname") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,
        s"""
                           <!DOCTYPE html>
           |<html>
           |<head>
           |    <meta name="viewport" content="width=device-width, initial-scale=1">
           |    <style>
           |body {font-family: Arial, Helvetica, sans-serif;}
           |* {box-sizing: border-box;}
           |
           |input[type=text], select, textarea {
           |  width: 100%;
           |  padding: 12px;
           |  border: 1px solid #ccc;
           |  border-radius: 4px;
           |  box-sizing: border-box;
           |  margin-top: 6px;
           |  margin-bottom: 16px;
           |  resize: vertical;
           |}
           |
           |input[type=submit] {
           |  background-color: #04AA6D;
           |  color: white;
           |  padding: 12px 20px;
           |  border: none;
           |  border-radius: 4px;
           |  cursor: pointer;
           |}
           |
           |input[type=submit]:hover {
           |  background-color: #45a049;
           |}
           |
           |.container {
           |  border-radius: 5px;
           |  background-color: #f2f2f2;
           |  padding: 20px;
           |}
           |</style>
           |</head>
           |<body>
           |
           |<center class="center"><h2 id="h">=====> Find One Student <====</h2>
           |
           |<div class="container">
           |   <form action="/action_page.php", method="get">
           |
           |       <label for="Class">Student_Class</label>
           |           |        <input type="number" id="lname" name="Class" placeholder="ZYour Sudent_Class.">
           |
           |       <label for="firstname">Student_firstname</label>
           |           |        <input type="text" id="lname" name="firstname" placeholder="ZYour Sudent_firstname.">
           |
           |                       <input type="submit" value="Submit">
           |    </form>
           |</div>
           |
           |</body>
           |</html>
           |""".stripMargin))
    }
  } ~ path("action_page.php") {
    get {
      parameters ('Class.as[Int],'firstname.as[String]){(Class, firstname)=>
      val s = Service.findByClassAndFirstname(Class , firstname)
       complete(s)
      }
    }
  }
}

    object ControlareStudentObject extends App {
      implicit val syatem = ActorSystem()
      implicit val materializer = ActorMaterializer()
      implicit val ec = syatem.dispatcher
      val port = 8080
      val host = "localhost"
      val routesObject = new htmlRouts()
      val routes = routesObject.sss11
      val httpRoutesOutput = Http().bindAndHandle(routes, host, port)
    }
