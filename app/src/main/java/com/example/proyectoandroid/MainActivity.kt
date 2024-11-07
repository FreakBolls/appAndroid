package com.example.proyectoandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private  lateinit var tvCorreoAdmin:TextView
    private  lateinit var bttnPrestamo:Button
    private  lateinit var bttnLibro:Button
    private  lateinit var bttnAutor:Button
    private  lateinit var tbnCerrarSesion:Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            volverInicio()
            return
        }

        bttnPrestamo = findViewById(R.id.bttnIrPrestamo)

        tvCorreoAdmin = findViewById(R.id.tvCorreoAdmin)

        bttnLibro = findViewById(R.id.bttnLibro)
        bttnAutor = findViewById(R.id.bttnAutor)
        tbnCerrarSesion = findViewById(R.id.tbnCerrarSesion)

        bttnAutor.setOnClickListener{irAutor()}
        bttnLibro.setOnClickListener{irlibro()}
        bttnPrestamo.setOnClickListener{irPrestamo()}


        auth= Firebase.auth
        var correo = auth.currentUser?.email.toString()
        tvCorreoAdmin.setText(correo)

        val bundle: Bundle?
        bundle = intent.extras
        val email=bundle?.getString("email")
        setup(email ?:"")
    }


    fun irlibro(){
        var ir = Intent(this,ListaLibroActivity::class.java)
        startActivity(ir)
    }
    fun irAutor(){
        var ir = Intent(this,ListaAutorActivity::class.java)
        startActivity(ir)
    }

    fun irPrestamo(){
        var ir = Intent(this,ListaPrestamoActivity::class.java)
        startActivity(ir)
    }

    fun setup(email:String){
        title="Menu"

        tbnCerrarSesion.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            volverInicio()
        }
    }



    fun volverInicio(){
        var intent=Intent(this,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}