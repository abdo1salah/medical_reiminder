package com.example.medicalreiminder.model.Api

import retrofit2.http.GET
import retrofit2.http.Url

interface BarcodeCallable {
    @GET
    suspend fun getData(@Url url:String): BarcodeResponse
}