package top.zhujm.appsearch

import kotlin.math.max

/**
 * @author: zhujiaming
 * @data: 2021/7/7
 * @description:
 **/

fun main() {
//    println("Hello world")
    var a: Int = 1000
    a = 10 * a
    println("a==>" + a)
    println(largeNumber(1, 2))


    var range = 0..10 //全闭合 [0,10]

    for (i in range) {
        println(i)
    }

    var range2 = 0 until 10 //[0,10)

    for (i in range2 step 2) { //每次跳过两个 0,2,4,6,

    }
}

//fun largeNumber(a: Int, b: Int): Int {
//    return max(a, b)
//}

fun largeNumber(a: Int, b: Int) = max(a, b)

fun langeNumber2(a: Int, b: Int) = if (a > b) a else b

fun getScore(name: String) = when (name) {
    "tom" -> 99
    "jack" -> 88
    "tina" -> {
        name.length / 2
    }
    else -> 0
}

fun checkNumber(num: Number) {
    when (num) {
        is Int -> println("is Int")
        is Double -> println("is Double")
        else -> println("i dont know")
    }
}

fun getScore2(name: String) = when {
    name.startsWith("tim") -> 88
    name == "jack" -> 88
    name == "tina" -> 33
    else -> 0

}


open class Person {

}

class Student : Person() {
    var sno = ""
    var grade = 0
}

class Student2(val sno: String, val grade: Int) : Person() {

}

class Student3(val sno: String, val grade: Int) : Person() {
    init {
        println(sno)
        println(grade)
    }
}

open class Person2(val name: String, val age: Int) {

}

class Student4(val sno: String, val grade: Int, name: String, age: Int) :
    Person2(name, age) {
    init {
        println(name)
        println(age)
    }
}

class Student5(val sno: String, val grade: Int, name: String, age: Int) : Person2(name, age) {
    constructor(name: String, age: Int) : this("", 1, name, age) {

    }

    constructor() : this("", 0) {

    }
}

class Student6 : Person2 {
    constructor(name: String, age: Int) : super(name, age){

    }
}


fun checkStu() {
    var stu2 = Student2("", 1)
    var stu4 = Student4("", 1, "tom", 3)
    var stu5 = Student5("", 12)
    var stu51 = Student5()
}