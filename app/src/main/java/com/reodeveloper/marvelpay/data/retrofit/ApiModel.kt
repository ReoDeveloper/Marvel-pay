package com.reodeveloper.marvelpay.data.retrofit

data class ApiThumbnail(val path: String, val extension: String)
data class ApiCharacter(val id: Long, val name: String, val thumbnail: ApiThumbnail)
data class ApiData(val offset: Int, val limit: Int, val total: Int, val count: Int, val results: List<ApiCharacter>)
data class ApiResult(val code:Int, val status:String, val copyright:String, val attribution:String, val data:ApiData)