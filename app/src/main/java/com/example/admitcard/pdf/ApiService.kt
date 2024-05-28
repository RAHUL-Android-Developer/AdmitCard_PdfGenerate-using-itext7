package com.example.admitcard.pdf

import com.example.admitcard.Model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("api/v1/admitcard/?studentId=19030624")
    fun getData(@Header("Authorization") token: String): Call<Model>
}

