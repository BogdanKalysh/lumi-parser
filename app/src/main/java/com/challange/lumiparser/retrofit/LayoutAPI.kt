package com.challange.lumiparser.retrofit

import retrofit2.Response

interface LayoutAPI {
    suspend fun getLayout(): Response<String>
}