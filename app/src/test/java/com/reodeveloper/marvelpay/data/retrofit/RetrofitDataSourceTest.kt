package com.reodeveloper.marvelpay.data.retrofit

import com.reodeveloper.common.MapperProvider
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RetrofitDataSourceTest : MockWebServerTest(){

    private lateinit var apiClient: RetrofitDataSource

    companion object {
        const val LIMIT = 50
    }

    @Before
    override fun setUp() {
        super.setUp()
        val mockWebServerEndpoint = baseEndpoint
        apiClient = RetrofitDataSource(MapperProvider.provideApiCharactersMapper(), mockWebServerEndpoint)
    }

    @Test
    fun sendsGetHasApiKey() {
        enqueueMockResponse(200, "characters.json")

        apiClient.getAll()

        assertRequestSentToContains("apiKey=e381644c13ec5251f83c4afc07a26a99")
    }

    @Test
    fun sendsGetCharactersToTheCorrectEndpoint() {
        enqueueMockResponse(200, "characters.json")

        apiClient.getAll()

        assertGetRequestSentTo("/characters?apiKey=e381644c13ec5251f83c4afc07a26a99&limit=50&orderBy=name")
    }

    @Test
    fun parsesAtMaxLimitSent() {
        enqueueMockResponse(200, "characters.json")

        val result = apiClient.getAll()

        assertEquals(LIMIT, result.size)
    }

}