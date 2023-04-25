package Programing

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object PatternMatchingQues extends App {
  for (b <- 'A' to 'F') {
    for (a <- 'A' to 'F') {
      if (a <= b) {
        print(a + " ")
      }
    }
    n = n + 1
    println("")
  }
  println("======================================================================================================")


  var n = 1
  for (b <- 'A' to 'F') {
    for (a <- 1 to n) {
      print(b + " ")

    }
    n = n + 1
    println("")
  }

  println("======================================================================================================")

  for (a <- 'A' to 'F') {
    for (b <- 'A' to 'F') {
      if (a >= b) {
        print(b + " ")
      }
    }
    println()
  }
  val list = List('E', 'D', 'C', 'B', 'A')
  for (a <- list) {
    for (b <- 'A' to 'E') {
      if (a >= b) {
        print(b + " ")
      }
    }
    println()
  }

  println("======================================================================================================")


  val list1 = List('F', 'E', 'D', 'C', 'B', 'A')

  for (a <- list1) {
    for (b <- 'A' to 'F') {
      if (a >= b) {
        print(b + " ")
      }
    }
    println()
  }

  for (a <- 'A' to 'F') {
    for (b <- 'A' to 'F') {
      if (a >= b) {
        print(b + " ")
      }
    }
    println()
  }


  println("======================================================================================================")

  val list11 = List('F', 'E', 'D', 'C', 'B', 'A')


  for (a <- list11) {
    for (b <- list11) {
      if (a >= b) {
        print(b + " ")
      }
    }
    println()
  }


  for (b <- 'B' to 'F') {
    for (a <- list11) {
      if (a <= b) {
        print(a + " ")
      }
    }
    println()
  }

  println("======================================================================================================")

  for (a <- 'A' to 'F') {
    for (b2 <- 'A' to 'F') {
      if (a <= b2) print(" ")
    }
    for (b2 <- 'A' to 'F') {
      if (a >= b2) print(b2 + " ")
    }
    println()
  }


  println("======================================================================================================")

/*

  val ss = ListBuffer( 'B', 'C', 'D', 'E', 'F')
  val list3 = mutable.Stack('F', 'E', 'D', 'C', 'B', 'A')

  val list2 = ListBuffer('A', 'B', 'C', 'D', 'E')
  var kk = 1
  val len = list2.length + list3.length

var u = -1

  for( a <- 0 until list3.length+list2.length){
    for(b1 <- list2){
      print(b1)
    }

    for ( b2<-1 to u  ) {
print(" ")
    }

    for (b1 <- list3) {
      print(b1)
    }
    println()

if(kk<=5) {
  list2 -= list3(0)
  list3 -= list3(0)
  kk += 1
  u+=2
}
else if(kk==10) {
  list3.push('F')
  u-=2
}
else {

  list2 += (ss(0))
  list3.push(ss(0))

  ss-=(ss(0))
  kk += 1
  u-=2
}
  }
*/


println("==================================================================================================================================")

  var s1 = "ABCDE"
  var s2 = "ABCDEF"
  var n1=5
  var ns = -1

  for(ab<- 0 to 10){

    if(((ab==0) || (ab==10))) {
      print(s1)
      println(s2.reverse)
    }
       else if (ab<6){print(s1.substring(0, n1))
     ns += 2
      for(al<- 1 to ns){
        print(" ")
      }
      println(s2.substring(0, n1).reverse)
      n1 -=1
  }
        else  {
   if(n1==0) n1+= 2 else n1 += 1
        print(s1.substring(0, n1))
      ns -= 2
      for (al <- 1 to ns) {
        print(" ")
      }
      println(s2.substring(0, n1).reverse)
    }
  }


  println("=========================  String pattern Questions  =======================================")

  val ques=  "What are you doing?"

    val hh=ques.split(" ")
    println(hh.foreach(println))

  println("==============================================================================================")

    val array = ques.toCharArray
    var string=""
    array.foreach{a=>string=a+string}
    println(string)
  println("=======================================================1=======================================")

  var array1 = ques.toCharArray
  var sum=0

  for(a<- 0 until array1.length){

   if(array1(a)==' '||array1.length-1==a){
     var f1= array1(sum)
     array1(sum)= array1(a-1)
     array1(a-1)=f1
     sum=a+1
   }
   }
  println(array1.foreach(print))

  println("=============================================================================================")

  val ques1=  "What are you doing?"
  val array2 = ques1.toCharArray
  sum=0
  var String1=""
  for (a <- 0 until array2.length) {
    if (array2(a) == ' ' || array2.length - 1 == a) {
      var St= ques1.substring(sum,a)

      sum=a+1
      String1 =  St+String1
      if(a ==array2.length-1){    String1 =  String1 + array2(a)  }
      String1 = " "+String1
    }
  }
   println(String1)
}
