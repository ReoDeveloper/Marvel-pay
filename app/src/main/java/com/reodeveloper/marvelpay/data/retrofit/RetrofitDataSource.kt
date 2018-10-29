package com.reodeveloper.marvelpay.data.retrofit

import com.reodeveloper.common.DataSource
import com.reodeveloper.marvelpay.data.Mapper
import com.reodeveloper.marvelpay.data.Specification
import com.reodeveloper.marvelpay.domain.model.Contact

class RetrofitDataSource(val mapper: Mapper<ApiCharacter, Contact>, url: String = BASE_URL) : DataSource<Contact> {

    companion object {
        val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
        val API_KEY = "e381644c13ec5251f83c4afc07a26a99"
        val LIMIT = 50
        val ORDER_BY = "name"
    }

    val apiService: RetrofitService = RetrofitClient.getClient(url)!!.create(RetrofitService::class.java)

    override fun store(item: Contact) {
        // This is method is not supported for the api
        throw UnsupportedOperationException("This Datasource has no storing capabilities")
    }

    override fun store(items: List<Contact>) {        // This is method is not supported for the api
        throw UnsupportedOperationException("This Datasource has no storing capabilities")
    }

    override fun getAll(): List<Contact> {
        val call = apiService.getCharacters(API_KEY, LIMIT, ORDER_BY)
        val callResponse = call.execute()
        if (callResponse?.isSuccessful!!) {
            val apiShopResponse = callResponse.body()
            apiShopResponse?.let {
                if (it.data.count > 0) {
                    return mapper.map(it.data.results)
                }
            }
        }
        return emptyList()
    }

    override fun queryList(specification: Specification): List<Contact> {
        throw NotImplementedError()
    }

    override fun queryItem(specification: Specification): Contact {
        throw NotImplementedError()
    }
}