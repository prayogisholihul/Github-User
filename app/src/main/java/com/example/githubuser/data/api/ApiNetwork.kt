package com.example.githubuser.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiNetwork {

    private const val baseUrl = "https://api.github.com/"
    private const val apiKey = "token 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"

    fun getClient(): ApiInterface {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Authorization", apiKey).build()
                it.proceed(request)
            }
            .build()

        val gson = GsonBuilder().serializeNulls().create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiInterface::class.java)
    }
}
