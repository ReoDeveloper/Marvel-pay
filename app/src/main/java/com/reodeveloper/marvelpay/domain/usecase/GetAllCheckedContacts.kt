package com.reodeveloper.marvelpay.domain.usecase

import android.os.Handler
import android.os.Looper
import com.reodeveloper.common.Repository
import com.reodeveloper.common.usecase.Result
import com.reodeveloper.common.usecase.ResultList
import com.reodeveloper.common.usecase.UseCase
import com.reodeveloper.marvelpay.data.SpecificationBySelected
import com.reodeveloper.marvelpay.domain.model.Contact

class GetAllCheckedContacts(repository: Repository<Contact>): UseCase<Contact>(repository){

    override fun execute(callback: Result<Contact>) {
        val result = repository.queryList(SpecificationBySelected())
        val runnable = Runnable {
            if(callback is ResultList<Contact>){
                callback.success(result)
            }else{
                callback.error("Wrong callback. Result is expected to be a List")
            }
        }
        Handler(Looper.getMainLooper()).post(runnable)
    }
}