package Programing



   class ArrayListP {
     var num = 10
     var list: Array[String] = new Array[String](num)
     var size = 0


     def IncrementSizeOfArray( ){
     if(size==num){
       num=num*2
       var list1= list
       list = new Array[String](num)
       for(a<- 0 until  size){
         list(a)=list1(a)
       }
     }
        }
     def add(data: String) {
       list(size) = data
       size += 1
       IncrementSizeOfArray( )
     }

     def removedata(data: String): Unit = {
       for (a <- 0 until (size)) {
         if (data == list(a)) {
           for (a1 <- a until (size)) {
             list(a1) = list(a1 + 1)
           }
           size -= 1
           return
         }
       }
     }

     def addOnIndex(Index: Int, data: String): Unit = {
       for (a <- 0 until (size)) {
         if (Index == a) {
           size += 1
           IncrementSizeOfArray( )
           val u = list(a)
           list(a) = data
           var su = 1
           for (a1 <- a + 1 until (size)) {
             list(size - su) = list(size - (su + 1))
             su += 1
           }
           list(a + 1) = u
           return
         }
       }
     }


     def printData(): Unit = {
    print("ArraList [")
    print( list(0) )
    for(a<- 1 until(size)){
     print( " , "+list(a) )
    }
    print("]")
    println()
  }
}

//===========================================================================================================

object o {
  def main(args: Array[String]): Unit = {
    val a=new  ArrayListP()
    a.add("s")
    a.add("d")
    a.add("f")
    a.add("a")
    a.add("z")
    a.add("o")
    a.add("j")
    a.add("l")
    a.add("b")
    a.add("t")
    a.add("c")
    a.add("b")
    a.add("t")
    a.add("cx")
    a.printData()
    a.removedata("f")
    a.printData()
    println(a.size)
    a.addOnIndex(2,"y")
    a.printData()
}
}