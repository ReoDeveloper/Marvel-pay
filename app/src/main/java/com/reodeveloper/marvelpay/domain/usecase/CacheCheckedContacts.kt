package com.reodeveloper.marvelpay.domain.usecase

import android.os.Handler
import android.os.Looper
import com.reodeveloper.common.Repository
import com.reodeveloper.common.usecase.Result
import com.reodeveloper.common.usecase.ResultUnit
import com.reodeveloper.common.usecase.UseCase
import com.reodeveloper.marvelpay.domain.model.Contact

class CacheCheckedContacts(repository: Repository<Contact>, val updated: List<Contact>) :
    UseCase<Contact>(repository) {

    override fun execute(callback: Result<Contact>) {
        repository.store(updated)
        val runnable = Runnable {
            if (callback is ResultUnit<Contact>) {
                callback.success()
            } else {
                callback.error("Wrong callback. Result is expected to be Unit")
            }
        }
        Handler(Looper.getMainLooper()).post(runnable)
    }
}