package com.housq.sunnyweather

import android.widget.Button
import android.widget.TextView

fun main() {
    val array:Array<Number>  = arrayOf(1.1,2.2)
    fill(array, 2)
    array.forEach { println(it) }
    val a = array[0]
    println(a!!::class.java.canonicalName)
}

fun  fill(array: Array<in Int>, t: Int) {
    array[0] = t

}

fun copy(srcArray:Array<out Number>, distArrat:Array<in Any>){
    for (index in srcArray.indices){
        distArrat[index] = srcArray[index]
    }
}
class Producer<out T>{
    val list = listOf<T>()
    fun produce():T{
        return list[0]
    }
}