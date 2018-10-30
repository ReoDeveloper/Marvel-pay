package com.reodeveloper.common.usecase

interface ResultUnit<T> : Result<T> {
    fun success()
}