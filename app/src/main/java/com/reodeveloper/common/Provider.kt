package com.reodeveloper.common

import android.content.Context

import com.reodeveloper.marvelpay.data.Mapper
import com.reodeveloper.marvelpay.data.retrofit.ApiCharacter
import com.reodeveloper.marvelpay.data.retrofit.RetrofitDataSource
import com.reodeveloper.marvelpay.data.retrofit.RfCharacterMapper
import com.reodeveloper.marvelpay.domain.model.Contact


class UseCaseProvider {
    companion object {
    }
}

class RepositoryProvider {
    companion object {

    }
}

class DataSourceProvider {
    companion object {
        fun provideContactRetrofitDataSource() = RetrofitDataSource(MapperProvider.provideApiCharactersMapper())
    }
}

class MapperProvider {
    companion object {
        fun provideApiCharactersMapper(): Mapper<ApiCharacter, Contact> = RfCharacterMapper()
    }
}