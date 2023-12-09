package com.example.android_tflite_classifymedicalplants.ModelHelper

import com.example.android_tflite_classifymedicalplants.Model.ServerResponse
import com.google.android.gms.common.api.Api
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.QueryMap

const val BASE_URL = "http://django.doannhat2.click:8000/"
interface API {
    @Multipart
    @POST("upload-image/")
    public fun getImageClass(@Part image: MultipartBody.Part ): Call<List<ServerResponse>?>?

    companion object {

    }
}
class RetrofitClient private constructor() {
    val myApi: API

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        myApi = retrofit.create(API::class.java)
    }

    companion object {
        @get:Synchronized
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }
            private set
    }
}
