package com.reodeveloper.marvelpay.data.room

import com.reodeveloper.marvelpay.data.TwoWayMapper
import com.reodeveloper.marvelpay.domain.model.Contact

class DbContactMapper : TwoWayMapper<DbContact, Contact>() {

    override fun map(item: DbContact): Contact {
        item.run {
            return Contact(
                name,
                phone,
                image
            )
        }
    }

    override fun reverseMap(item: Contact): DbContact {
        item.run {
            return DbContact(
                name,
                phone,
                avatar
            )
        }
    }

}