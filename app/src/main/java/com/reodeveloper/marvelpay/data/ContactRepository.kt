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

    override fun store(items: List<Contact>) {
        cache?.store(items)
    }

    override fun getAll(): List<Contact> {
        cache?.getAll()?.run { if (isNotEmpty()) return this }
        return apiDataSource.getAll().plus(deviceDataSource.getAll()).sortedBy { it.name }.also { cache?.store(it) }
    }

    override fun queryList(specification: Specification): List<Contact> {
        return when (specification) {
            is SpecificationByPage -> apiDataSource.queryList(specification).sortedBy { it.name }.also { cache?.store(it) }
            else -> cache?.queryList(specification) ?: emptyList()
        }
    }

    override fun queryItem(specification: Specification): Contact {
        throw NotImplementedError()
    }
}