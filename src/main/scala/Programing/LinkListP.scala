package Programing

class LinkListP {

  var Head:Node = null
  private var size=0
  var Head1:Node =_

  class Node(Data:Any){
    var data =Data
    var priv:Node=null
    var next:Node=null
  }

  def AddData(Data:Any): Unit = {
    if(size==0){
      Head= new Node(Data)
      size+=1
    }
    else {
      val s=new Node(Data)
      Head.next = s
      s.priv=Head
      Head=s
      size+=1
    }
  }

  def findData(): Unit = {
    if (size == 0) {
      println("List is Eimpty")
    } else {
      print("[")
      var current = Head
      while (current != null) {

        print(current.data)
        if (current.priv != null) {
          print(" -> ")
        }
        current = current.priv
      }
      println("]")
    }
  }


  def size1(): Unit = {
    println(this.size)
  }


  def find(): Unit = {
   var ds:Node =null
    if(size==0){
      println("data not available")
    } else {
      var curr=Head

      while(curr!=null) {
        if( curr.priv!=null) ds=curr.priv
        curr=curr.priv
      }
    }
    var curr1=ds
    if(size!=0){
      print("[ ")
      while (curr1 != null) {
        print(curr1.data + " ")
        if (curr1.next != null) {
          print(" -> ")
          ds = curr1.next
        }
        curr1 = curr1.next
      }
      println("]")

    }

  }

  def delete(): Unit = {
    var ds: Node = null
    if (size == 0) {
      println("data not available")
    } else {
      println(Head.priv)
    }}

  def +=(Data: Any): Unit = {
    if (size == 0) {
      Head = new Node(Data)
      size += 1
    }
    else {
      val s = new Node(Data)
      Head.next = s
      s.priv = Head
      Head = s
      size += 1
    }}}

object s extends App{

  val s=new LinkListP()
  s.AddData("a")
  s.AddData("f")
  s.AddData("g")
  s.AddData("j")
  s.AddData("s")
  s.find()
  s+="hh"
  s.find()
  s.delete()
  s.find()
}

