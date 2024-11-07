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
import com.example.proyectoandroid.adaptador.LibroAdapter
import com.example.proyectoandroid.controlador.AutorController
import com.example.proyectoandroid.controlador.LibroController
import com.example.proyectoandroid.entidad.Autor
import com.example.proyectoandroid.entidad.Libro
import com.example.proyectoandroid.utils.AppConfig
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ListaLibroActivity:AppCompatActivity() {
    private lateinit var rvLibro:RecyclerView
    private lateinit var bttnRegistar: Button
    private  lateinit var txtFiltro:TextInputEditText
    private  lateinit var bttnBuscar:Button
    private  lateinit var BttMenu:Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listar_libro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bttnRegistar=findViewById(R.id.bttnNuevoLibro)
        rvLibro=findViewById(R.id.rvLibro)
        txtFiltro = findViewById(R.id.txtFiltroTitulo)
        bttnBuscar = findViewById(R.id.bttnBuscarLibro)
        BttMenu = findViewById(R.id.bttnMenuPrincipal)

        val libroController = LibroController()
        val autorController = AutorController()


        val data = libroController.findAllLibro()
        val autores = autorController.findAllAutor()

        var adaptador= LibroAdapter(data,autores)
        rvLibro.layoutManager= LinearLayoutManager(this)
        rvLibro.adapter=adaptador

        bttnRegistar.setOnClickListener { registrar() }
        BttMenu.setOnClickListener { menu() }

        bttnBuscar.setOnClickListener {
            val titulo= txtFiltro.text.toString()
            val libros = libroController.buscarLibroTitulo(titulo)
            adaptador = LibroAdapter(libros,autores)
            rvLibro.adapter = adaptador
        }

    }

    fun registrar(){
        var intent=Intent(this,RegistrarLibroActivity::class.java)
        startActivity(intent)
    }

    fun menu(){
        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}