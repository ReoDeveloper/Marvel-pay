package com.reodeveloper.marvelpay.data

import com.reodeveloper.common.DataSource
import com.reodeveloper.common.Repository
import com.reodeveloper.marvelpay.domain.model.Contact

class ContactRepository(val apiDataSource: DataSource<Contact>, val deviceDataSource: DataSource<Contact>) :
    Repository<Contact>(deviceDataSource) {

    var cache: DataSource<Contact>? = null

    override fun store(item: Contact) {
        cache?.store(item)
    }

    override fun getAll(): List<Contact> {
        cache?.getAll()?.run { if (isNotEmpty()) return this }
        return apiDataSource.getAll().plus(deviceDataSource.getAll()).sortedBy { it.name }.also { cache?.store(it) }
    }

    override fun queryList(specification: Specification): List<Contact> {
        throw NotImplementedError()
    }

    override fun queryItem(specification: Specification): Contact {
        throw NotImplementedError()
    }
}