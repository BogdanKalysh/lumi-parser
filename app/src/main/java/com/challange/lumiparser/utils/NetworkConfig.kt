package com.challange.lumiparser.utils

import android.content.Context
import com.challange.lumiparser.R
import com.challange.lumiparser.BuildConfig

object NetworkConfig {
    fun provideBaseUrl(context: Context): String {
        return if (BuildConfig.BASE_URL == "DEFAULT") {
            context.getString(R.string.default_base_url)
        } else {
            BuildConfig.BASE_URL
        }
    }
}
