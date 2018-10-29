package com.reodeveloper.common

import android.content.Context
import com.reodeveloper.marvelpay.data.ContactRepository

import com.reodeveloper.marvelpay.data.Mapper
import com.reodeveloper.marvelpay.data.device.DeviceContact
import com.reodeveloper.marvelpay.data.device.DeviceContactDataSource
import com.reodeveloper.marvelpay.data.device.DeviceContactMapper
import com.reodeveloper.marvelpay.data.retrofit.ApiCharacter
import com.reodeveloper.marvelpay.data.retrofit.RetrofitDataSource
import com.reodeveloper.marvelpay.data.retrofit.RfCharacterMapper
import com.reodeveloper.marvelpay.domain.model.Contact
import com.reodeveloper.marvelpay.domain.usecase.GetAllContacts


class UseCaseProvider {
    companion object {
        fun provideGetAllContactsUseCase(context: Context) =
            GetAllContacts(RepositoryProvider.provideContactRepository(context))
    }
}

class RepositoryProvider {
    companion object {
        fun provideContactRepository(context: Context) =
            ContactRepository(
                DataSourceProvider.provideContactRetrofitDataSource(),
                DataSourceProvider.provideContactDeviceDataSource(context)
            )
    }
}

class DataSourceProvider {
    companion object {
        fun provideContactRetrofitDataSource() = RetrofitDataSource(MapperProvider.provideApiCharactersMapper())
        fun provideContactDeviceDataSource(context: Context) =
            DeviceContactDataSource(context, MapperProvider.provideDeviceContactMapper())
    }
}

class MapperProvider {
    companion object {
        fun provideApiCharactersMapper(): Mapper<ApiCharacter, Contact> = RfCharacterMapper()
        fun provideDeviceContactMapper(): Mapper<DeviceContact, Contact> = DeviceContactMapper()
    }
}