package com.reodeveloper.marvelpay.data.device

import android.content.Context
import com.reodeveloper.common.DataSource
import com.reodeveloper.marvelpay.data.Mapper
import com.reodeveloper.marvelpay.data.Specification
import com.reodeveloper.marvelpay.domain.model.Contact

class DeviceContactDataSource(val context: Context, val mapper: Mapper<DeviceContact, Contact>) : DataSource<Contact> {

    override fun getAll(): List<Contact> {
        return mapper.map(ContactResolver(context).getContactsInfo())
    }

    override fun queryList(specification: Specification): List<Contact> {
        throw NotImplementedError()
    }

    override fun queryItem(specification: Specification): Contact {
        throw NotImplementedError()
    }


}