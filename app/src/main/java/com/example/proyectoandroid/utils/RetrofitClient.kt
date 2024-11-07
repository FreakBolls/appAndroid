package com.example.proyectoandroid.utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {

        fun getClient(URL: String): Retrofit {
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd")  // Solo fecha, sin hora
                .create()

            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}