package com.challange.lumiparser

import androidx.room.Room
import com.challange.lumiparser.retrofit.LayoutAPI
import com.challange.lumiparser.retrofit.LayoutAPIImpl
import com.challange.lumiparser.room.LayoutDao
import com.challange.lumiparser.room.LayoutDatabase
import com.challange.lumiparser.room.LayoutRepository
import com.challange.lumiparser.room.LayoutRepositoryImpl
import com.challange.lumiparser.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

val appModule = module {
    single <LayoutAPI> {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.server_name))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(LayoutAPIImpl::class.java)
    }

    single <LayoutDao> {
        Room.databaseBuilder(
            androidContext(),
            LayoutDatabase::class.java,
            androidContext().getString(R.string.database_name)
        ).build().dao
    }
    single <LayoutRepository> {
        LayoutRepositoryImpl(get())
    }

    viewModelOf(::MainViewModel)
}