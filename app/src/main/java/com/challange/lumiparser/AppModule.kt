package com.challange.lumiparser

import com.challange.lumiparser.retrofit.LayoutAPI
import com.challange.lumiparser.retrofit.LayoutAPIImpl
import com.challange.lumiparser.retrofit.model.LayoutElement
import com.challange.lumiparser.viewmodel.MainViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single {
        Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(LayoutElement::class.java, "type")
                    .withSubtype(LayoutElement.Page::class.java, "page")
                    .withSubtype(LayoutElement.Section::class.java, "section")
                    .withSubtype(LayoutElement.Text::class.java, "text")
                    .withSubtype(LayoutElement.Image::class.java, "image")
            )
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single <LayoutAPI> {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.server))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
            .create(LayoutAPIImpl::class.java)
    }

    viewModelOf(::MainViewModel)
}