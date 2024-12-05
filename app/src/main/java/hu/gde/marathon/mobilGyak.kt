package hu.gde.marathon

data class Person(val age : Int, val name: String)
{
    lateinit var bithPlace: String
    var height: Int? = null

}

fun main()
{
   var list = mutableListOf(1, 2, 3)

    list.add(133)
    println(list)

    list.swap(0, 3)

    println(list)

}

fun MutableList<Int>.swap(index1: Int, index2: Int)
{
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

