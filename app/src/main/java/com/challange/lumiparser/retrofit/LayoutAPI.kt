package com.challange.lumiparser.retrofit

import com.challange.lumiparser.retrofit.model.LayoutElement
import retrofit2.Response

interface LayoutAPI {
    suspend fun getLayout(): Response<LayoutElement>
}