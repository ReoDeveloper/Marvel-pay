package com.reodeveloper.marvelpay.data.retrofit

import com.reodeveloper.common.DataSource
import com.reodeveloper.marvelpay.BuildConfig
import com.reodeveloper.marvelpay.data.Mapper
import com.reodeveloper.marvelpay.data.Specification
import com.reodeveloper.marvelpay.domain.model.Contact
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class RetrofitDataSource(val mapper: Mapper<ApiCharacter, Contact>, url: String = BASE_URL) : DataSource<Contact> {

    companion object {
        val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
        val API_KEY = "e381644c13ec5251f83c4afc07a26a99"
        // We store our API_SECRET on ~/.gradle/gradle.properties
        val LIMIT = 50
        val ORDER_BY = "name"
    }

    val apiService: RetrofitService = RetrofitClient.getClient(url)!!.create(RetrofitService::class.java)

    override fun store(item: Contact) {
        // This is method is not supported for the api
        throw UnsupportedOperationException("This Datasource has no storing capabilities")
    }

    override fun store(items: List<Contact>) {
        // This is method is not supported for the api
        throw UnsupportedOperationException("This Datasource has no storing capabilities")
    }

    override fun getAll(): List<Contact> {
        val ts = (System.currentTimeMillis()/1000).toString()
        val hash = MD5(ts + BuildConfig.MarvelApiSecret + API_KEY)

        val call = apiService.getCharacters(API_KEY, LIMIT, ORDER_BY, ts, hash)
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


    private fun MD5(input: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            val md5Data = BigInteger(1, md.digest(input.toByteArray()))
            return String.format("%032X", md5Data).toLowerCase()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return ""
        }
    }
}