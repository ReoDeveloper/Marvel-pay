package com.reodeveloper.marvelpay.data

abstract class Mapper<I,O> {
    abstract fun map(item:I):O
    fun map(items:List<I>):List<O> = items.map { map(it) }
}