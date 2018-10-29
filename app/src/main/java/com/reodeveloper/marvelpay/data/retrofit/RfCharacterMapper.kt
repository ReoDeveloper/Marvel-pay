package com.reodeveloper.marvelpay.data.retrofit

import com.reodeveloper.marvelpay.data.Mapper
import com.reodeveloper.marvelpay.domain.model.Contact

class RfCharacterMapper : Mapper<ApiCharacter, Contact>() {

    override fun map(item: ApiCharacter): Contact {
        return Contact(
            item.name,
            null,
            item.thumbnail.path + "." + item.thumbnail.extension
        )
    }
}