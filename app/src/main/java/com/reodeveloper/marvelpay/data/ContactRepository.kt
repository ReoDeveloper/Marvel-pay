package com.reodeveloper.marvelpay.data

import com.reodeveloper.common.DataSource
import com.reodeveloper.common.Repository
import com.reodeveloper.marvelpay.domain.model.Contact

class ContactRepository(val apiDataSource: DataSource<Contact>, val deviceDataSource: DataSource<Contact>) :
    Repository<Contact>(deviceDataSource) {

    override fun getAll(): List<Contact> {
        return apiDataSource.getAll().plus(deviceDataSource.getAll()).sortedBy { it.name }
    }

    override fun queryList(specification: Specification): List<Contact> {
        throw NotImplementedError()
    }

    override fun queryItem(specification: Specification): Contact {
        throw NotImplementedError()
    }
}