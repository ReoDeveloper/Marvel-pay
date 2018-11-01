package com.reodeveloper.marvelpay.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("characters")
    fun getCharacters(
        @Query("apikey") apiKey: String,
        @Query("limit") limit: Int,
        @Query("orderBy") orderBy: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<ApiResult>

    @GET("characters")
    fun getCharactersByOffset(
        @Query("apikey") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("orderBy") orderBy: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String
    ): Call<ApiResult>
}