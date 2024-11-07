package com.example.proyectoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.services.ApiServicePrestamo
import com.example.proyectoandroid.adaptador.PrestamoAdapter
import com.example.proyectoandroid.entidad.Libro
import com.example.proyectoandroid.entidad.Prestamo
import com.example.proyectoandroid.utils.ApiUtils

import com.example.proyectoandroid.utils.AppConfig
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaPrestamoUsuarioActivity:AppCompatActivity() {

    private lateinit var rvPrestamo:RecyclerView
    private lateinit var bttnRegistar: Button

    private lateinit var Menu: Button

    private lateinit var api: ApiServicePrestamo

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listar_prestamo_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth= Firebase.auth
        var correo = auth.currentUser?.email.toString()

        bttnRegistar=findViewById(R.id.bttnNuevoPrestamo)
        rvPrestamo=findViewById(R.id.rvPrestamo)

        Menu=findViewById(R.id.bttnMenuPrincipal)
        //
        api= ApiUtils.getApiPrestamo()
        //
        bttnRegistar.setOnClickListener { registrar() }
        Menu.setOnClickListener { menu() }

        listado(correo)
    }

        fun listado(idUsuario:String){
            //invocar a la función findAll
            api.findByIdUsuario(idUsuario).enqueue(object: Callback<List<Prestamo>>{
                override fun onResponse(call: Call<List<Prestamo>>, response: Response<List<Prestamo>>) {
                    if(response.isSuccessful){
                        var data=response.body()!!
                        var adaptador=PrestamoAdapter(data)
                        rvPrestamo.layoutManager=LinearLayoutManager(AppConfig.CONTEXTO)
                        rvPrestamo.adapter=adaptador
                    }
                }
                override fun onFailure(call: Call<List<Prestamo>>, t: Throwable) {
                    showAlert(t.localizedMessage)
                }
            })
        }

    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Datos")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    fun registrar(){
        var intent=Intent(this,RegistrarPrestamoActivity::class.java)
        startActivity(intent)
    }


    fun menu(){
        var intent=Intent(this,UsuarioMainActivity::class.java)
        startActivity(intent)
        finish()
    }



}