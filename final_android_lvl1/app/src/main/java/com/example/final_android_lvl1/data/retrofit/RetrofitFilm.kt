package com.example.final_android_lvl1.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitFilm @Inject constructor() {
    private val interceptor = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(API_KEY))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(interceptor)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val getFilm: FinishURL = retrofit.create(FinishURL::class.java)


    companion object {

        // const val API_KEY = "5ab71e95-b401-47b3-b5ad-c86f795e6a95"
        //const val API_KEY = "22e40f79-6f46-4802-92de-86854b6b996f"
        //const val API_KEY ="e40ade0d-c03e-443d-a1d5-3b5e1932d96f"
        //const val API_KEY = "7569eb5f-1853-498a-944f-15212327e21f"

        //const val API_KEY = "5140e345-2740-40af-ad3c-6d751bd2ec31"
        //const val API_KEY = "c1251543-9bcb-41b9-8b1c-20a5570f5a48"
        const val API_KEY = "8d3bbbec-05d7-45c6-bcae-1091b2fb2387"
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    }
}
