package com.challange.lumiparser

import android.app.Application
import com.challange.lumiparser.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LumiParserApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LumiParserApplication)
            modules(appModule)
        }
    }
}