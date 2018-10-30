package com.reodeveloper.marvelpay.data

abstract class TwoWayMapper<I, O>: Mapper<I, O>() {
    abstract fun reverseMap(item:O):I

    fun reverseMap(items:List<O>):List<I> {
        return items.map { reverseMap(it) }
    }
}