package com.challange.lumiparser.retrofit

import com.challange.lumiparser.retrofit.model.LayoutElement
import retrofit2.Response
import retrofit2.http.GET

interface LayoutAPIImpl: LayoutAPI {
    @GET("v1/f118b9f0-6f84-435e-85d5-faf4453eb72a")
    override suspend fun getLayout(): Response<LayoutElement>
}