package com.reodeveloper.common.usecase

interface Result<T> {
    fun error(message:String)
}