package com.reodeveloper.marvelpay.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("characters")
    fun getCharacters(
        @Query("apiKey") apiKey: String,
        @Query("limit") limit: Int,
        @Query("orderBy") orderBy: String
    ): Call<ApiResult>
}