package com.reodeveloper.marvelpay.data.device

import com.reodeveloper.marvelpay.data.Mapper
import com.reodeveloper.marvelpay.domain.model.Contact

class DeviceContactMapper : Mapper<DeviceContact, Contact>() {

    override fun map(item: DeviceContact): Contact {
        with(item){
            return Contact(name, number ?: "", image ?: "")
        }
    }
}