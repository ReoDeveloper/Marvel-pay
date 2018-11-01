package com.reodeveloper.marvelpay.data.retrofit

import com.reodeveloper.common.DataSource
import com.reodeveloper.marvelpay.BuildConfig
import com.reodeveloper.marvelpay.data.Mapper
import com.reodeveloper.marvelpay.data.Specification
import com.reodeveloper.marvelpay.data.SpecificationByPage
import com.reodeveloper.marvelpay.data.SpecificationBySelected
import com.reodeveloper.marvelpay.domain.model.Contact
import retrofit2.Call
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
        val ts = getTimestamp()
        val call = apiService.getCharacters(API_KEY, LIMIT, ORDER_BY, ts, getHashForTimestamp(ts))
        return queryList(call)
    }

    override fun queryList(specification: Specification): List<Contact> {
        return when (specification) {
            is SpecificationByPage -> {
                queryListByPage(specification.page)
            }
            else -> emptyList()
        }
    }

    private fun getTimestamp() = (System.currentTimeMillis() / 1000).toString()

    private fun getHashForTimestamp(timestamp: String) = MD5(timestamp + BuildConfig.MarvelApiSecret + API_KEY)

    private fun queryListByPage(value: Int): List<Contact> {
        val ts = getTimestamp()
        val call =
            apiService.getCharactersByOffset(API_KEY, LIMIT, LIMIT * value, ORDER_BY, ts, getHashForTimestamp(ts))
        return queryList(call)
    }

    private fun queryList(call: Call<ApiResult>): List<Contact> {
        val callResponse = call.execute()
        if (callResponse?.isSuccessful!!) {
            val apiResponse = callResponse.body()
            apiResponse?.let {
                if (it.data.count > 0) {
                    return mapper.map(it.data.results)
                }
            }
        }
        return emptyList()
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