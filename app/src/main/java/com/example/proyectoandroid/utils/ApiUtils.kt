package com.example.proyectoandroid.utils

import com.example.proyectoandroid.services.ApiServicePrestamo

class ApiUtils {

    companion object {
        val BASE_URL = "http://10.0.2.2:5105/api/"
        //val BASE_URL="http://www.webapiandroid.somee.com/api/"

        fun getApiPrestamo(): ApiServicePrestamo {
            return RetrofitClient.getClient(BASE_URL).create(ApiServicePrestamo::class.java)
        }
    }
}