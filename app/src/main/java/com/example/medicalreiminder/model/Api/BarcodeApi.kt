package com.example.medicalreiminder.model.Api

import com.example.medicalreiminder.model.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BarcodeApi {
    val retrofitService : BarcodeCallable by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(BarcodeCallable::class.java)

    }
}