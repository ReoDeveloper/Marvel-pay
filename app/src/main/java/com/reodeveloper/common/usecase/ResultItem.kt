package com.reodeveloper.common.usecase

interface ResultItem<T> : Result<T> {
    fun success(item: T)
}