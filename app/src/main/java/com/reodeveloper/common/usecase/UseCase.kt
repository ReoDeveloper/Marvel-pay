package com.reodeveloper.common.usecase

import com.reodeveloper.common.Repository


abstract class UseCase<T>(val repository: Repository<T>) {
    abstract fun execute(callback: Result<T>)
}
