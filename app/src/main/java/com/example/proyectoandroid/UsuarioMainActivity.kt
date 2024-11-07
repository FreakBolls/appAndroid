package com.example.proyectoandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class UsuarioMainActivity : AppCompatActivity() {

    private  lateinit var tvCorreo:TextView
    private  lateinit var bttnPrestamo:Button
    private  lateinit var bttnLibro:Button
    private  lateinit var bttnAutor:Button
    private  lateinit var tbnCerrarSesion:Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.usuario_activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth= Firebase.auth

        tvCorreo = findViewById(R.id.tvCorreo)
        bttnLibro = findViewById(R.id.bttnLibro)
        bttnAutor = findViewById(R.id.bttnAutor)
        bttnPrestamo = findViewById(R.id.bttnPrestamo)

        tbnCerrarSesion = findViewById(R.id.tbnCerrarSesion)

        var correo = auth.currentUser?.email.toString()

        bttnAutor.setOnClickListener{irAutor(correo)}
        bttnLibro.setOnClickListener{irlibro(correo)}
        bttnPrestamo.setOnClickListener{irPrestamo(correo)}


        5
        showToolbar("Menu Usuario", true)

    }

    fun irlibro(email:String){
        var ir = Intent(this,ListaLibroUsuarioActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(ir)
    }

    fun irAutor(email:String){
        var ir = Intent(this,ListaAutorUsuarioActivity::class.java).apply {
            putExtra("email",email)

        }
        startActivity(ir)
    }

    fun irPrestamo(email:String){
        var ir = Intent(this,ListaPrestamoUsuarioActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(ir)
    }

    fun setup(email:String){
        title="Menu"
        tvCorreo.text=email
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

    fun showToolbar(title: String?, upButton: Boolean) {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}