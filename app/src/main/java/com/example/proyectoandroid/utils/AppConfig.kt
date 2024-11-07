package com.example.proyectoandroid.utils

import android.app.Application
import android.content.Context
import com.example.proyectoandroid.data.IniBd


class AppConfig:Application() {

    companion object{
        lateinit var CONTEXTO:Context
        lateinit var BD:IniBd
        var NOMBRE_BD="biblioteca.bd"
        var VERSION=1
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXTO=applicationContext
        BD=IniBd()
    }
}