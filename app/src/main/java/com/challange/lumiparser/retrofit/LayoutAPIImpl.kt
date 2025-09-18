package com.challange.lumiparser.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface LayoutAPIImpl: LayoutAPI {
    @GET("devicer_backend_static/layout.json")
    override suspend fun getLayout(): Response<String>
}