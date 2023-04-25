package Programing


class StackP {
  var Head: Node = null
  var Size = 0

  class Node(data: Any) {
    var Data: Any = data
    var next: Node = null
  }

  def addData(data: Any): Unit = {
    if (Size == 0) {
      this.Head = new Node(data)
      Size += 1
    } else {
      var s = new Node(data)
      s.next = Head
      Head = s
      Size += 1
    }
  }

  def deleteData(): Unit = {
    if (Size == 0) {
    println("List is Eimpty")
    } else {
      Head=Head.next
      Size -= 1
    }
  }


  def findData(): Unit = {
    if (Size == 0) {
      println("List is Eimpty")
    } else {
      print("[")
    var current = Head
    while (current != null) {

      print( current.Data)
      if(current.next!=null){print( " -> ")}
      current = current.next
    }
    println("]")
  }}
  def size(): Unit = {
    println(this.Size)
  }
}

object ss extends StackP {
  def main(args: Array[String]): Unit = {
    val s= new StackP()


    s.addData("a")
    s.addData("g")
    s.addData("d")
    s.addData("b")


    s.findData()
    s.deleteData()
    s.findData()
  s.size
  }
}