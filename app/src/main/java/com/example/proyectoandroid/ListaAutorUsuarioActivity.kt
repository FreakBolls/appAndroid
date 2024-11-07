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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.adaptador.AutorAdapter
import com.example.proyectoandroid.controlador.AutorController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth


class ListaAutorUsuarioActivity:AppCompatActivity() {
    private  lateinit var rvAutor: RecyclerView
    private  lateinit var tvCorreo: TextView
    private  lateinit var txtFiltro:TextInputEditText
    private  lateinit var bttnBuscar:Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listar_autor_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showToolbar("Listado de Autores", true)

        rvAutor=findViewById(R.id.rvAutor)
        tvCorreo=findViewById(R.id.tvCorreo)
        txtFiltro = findViewById(R.id.txtFiltroAutor)
        bttnBuscar = findViewById(R.id.bttnFiltrarAutor)


        val autorController = AutorController()
        val autores = autorController.findAllAutor()

        var adaptador= AutorAdapter(autores)
        rvAutor.layoutManager= LinearLayoutManager(this)
        rvAutor.adapter=adaptador

      


        bttnBuscar.setOnClickListener {
                val nombrexape= txtFiltro.text.toString()
                val autores = autorController.buscarAutor(nombrexape)
                adaptador = AutorAdapter(autores)
                rvAutor.adapter = adaptador
        }

        val bundle: Bundle?
        bundle = intent.extras
        val email=bundle?.getString("email")
        setup(email ?:"")
    }

    fun setup(email:String){
        title="Menu"
        tvCorreo.text=email
        // tbnCerrarSesion.setOnClickListener{
        //    FirebaseAuth.getInstance().signOut()
        //   volverInicio()
        //}

    }


    fun showToolbar(title: String?, upButton: Boolean) {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}