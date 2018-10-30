package com.reodeveloper.marvelpay.data.room

import android.content.Context
import com.reodeveloper.common.DataSource
import com.reodeveloper.marvelpay.data.Specification
import com.reodeveloper.marvelpay.data.SpecificationBySelected
import com.reodeveloper.marvelpay.data.TwoWayMapper
import com.reodeveloper.marvelpay.domain.model.Contact

class RoomDataSource(context: Context, val mapper: TwoWayMapper<DbContact, Contact>) : DataSource<Contact> {

    var database: MarvelDatabase = MarvelDatabase.getInstance(context)

    override fun store(item: Contact) {
        database.contactsDao().insert(mapper.reverseMap(item))
    }

    override fun store(items: List<Contact>) {
        items.map { store(it) }
    }

    override fun getAll(): List<Contact> {
        return mapper.map(database.contactsDao().getAll())
    }

    override fun queryList(specification: Specification): List<Contact> {
        return when (specification) {
            is SpecificationBySelected -> {
                val result = mapper.map(database.contactsDao().querySelectedItems())
                // We reset cached values, since we already asked for them
                database.contactsDao().restorePreviousSelectedItems()
                result
            }
            else -> emptyList()
        }
    }

    override fun queryItem(specification: Specification): Contact {
        throw NotImplementedError()
    }
}