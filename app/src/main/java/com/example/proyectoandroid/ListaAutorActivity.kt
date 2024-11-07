package com.example.proyectoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.adaptador.AutorAdapter
import com.example.proyectoandroid.controlador.AutorController
import com.example.proyectoandroid.entidad.Autor
import com.example.proyectoandroid.utils.AppConfig
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ListaAutorActivity:AppCompatActivity() {
    private  lateinit var rvAutor: RecyclerView
    private  lateinit var bttnRegistar: Button
    private  lateinit var txtFiltro:TextInputEditText
    private  lateinit var bttnBuscar:Button
    private  lateinit var BttnMenu:Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listar_autor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bttnRegistar=findViewById(R.id.bttnNuevoAutor)
        rvAutor=findViewById(R.id.rvAutor)
        txtFiltro = findViewById(R.id.txtFiltroAutor)
        bttnBuscar = findViewById(R.id.bttnFiltrarAutor)
        BttnMenu = findViewById(R.id.bttnMenuPrincipal)

        val autorController = AutorController()
        val autores = autorController.findAllAutor()

        var adaptador= AutorAdapter(autores)
        rvAutor.layoutManager= LinearLayoutManager(this)
        rvAutor.adapter=adaptador

        bttnRegistar.setOnClickListener { registrar() }
        BttnMenu.setOnClickListener { menu() }



        bttnBuscar.setOnClickListener {
                val nombrexape= txtFiltro.text.toString()
                val autores = autorController.buscarAutor(nombrexape)
                adaptador = AutorAdapter(autores)
                rvAutor.adapter = adaptador
            }
    }

    fun menu(){
        var menu=Intent(this,MainActivity::class.java)
        startActivity(menu)
        finish()
    }



    fun registrar(){
        var intent=Intent(this,RegistrarAutorActivity::class.java)
        startActivity(intent)

    }
}