package com.reodeveloper.common.usecase

interface ResultList<T> : Result<T> {
    fun success(items: List<T>)
}