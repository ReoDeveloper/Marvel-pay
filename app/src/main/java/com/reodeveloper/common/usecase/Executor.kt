package com.reodeveloper.common.usecase

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class Executor {
    companion object {
        val INITIAL_POOL_SIZE = 1
        val MAX_POOL_SIZE = 2
        val KEEP_ALIVE_TIME = 10L
        val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
        val threadPoolExecutor = ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, LinkedBlockingQueue())
        var INSTANCE: Executor? = null

        fun getInstance(): Executor {
            if (INSTANCE == null) {
                synchronized(Executor::class.java) {
                    INSTANCE = Executor()
                }
            }
            return INSTANCE!!
        }
    }

    fun <T> execute(useCase: UseCase<T>, callback: Result<T>) {
        Runnable { useCase.execute(callback) }.also { threadPoolExecutor.execute(it) }
    }
}