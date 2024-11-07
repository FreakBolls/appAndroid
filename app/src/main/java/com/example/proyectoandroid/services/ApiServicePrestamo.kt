package com.example.proyectoandroid.services

import com.example.proyectoandroid.entidad.Prestamo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServicePrestamo {
 //endpoint
 @GET("Prestamo/lista")
 fun findAll():Call<List<Prestamo>>
 //endpoint
 @POST("Prestamo/Guardar")
 fun save(@Body prestamo:Prestamo):Call<Prestamo>

 //endpoint
 @GET("prestamo/ObtenerPrestamo/{codigo}")
 fun findById(@Path("codigo") cod:Int):Call<Prestamo>

 @GET("prestamo/ObtenerPorUsuario/{idUsuario}")
 fun findByIdUsuario(@Path("idUsuario") idUsuario:String): Call<List<Prestamo>>

 @PUT("prestamo/EditarPrestamo")
 fun update(@Body med:Prestamo):Call<Prestamo>

 @DELETE("Prestamo/EliminarPrestamo/{codigo}")
 fun deleteById(@Path("codigo") cod:Int):Call<Void>

}