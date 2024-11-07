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
import com.example.proyectoandroid.adaptador.LibroAdapter
import com.example.proyectoandroid.controlador.AutorController
import com.example.proyectoandroid.controlador.LibroController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ListaLibroUsuarioActivity:AppCompatActivity() {
    private lateinit var rvLibro:RecyclerView
    private lateinit var tvCorreo: TextView
    private  lateinit var txtFiltro:TextInputEditText
    private  lateinit var bttnBuscar:Button
    private  lateinit var BttMenu:Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listar_libro_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showToolbar("Listado de Libros", true)

        rvLibro=findViewById(R.id.rvLibro)
        txtFiltro = findViewById(R.id.txtFiltroTitulo)
        tvCorreo = findViewById(R.id.tvCorreo)
        bttnBuscar = findViewById(R.id.bttnBuscarLibro)


        val libroController = LibroController()
        val autorController = AutorController()


        val data = libroController.findAllLibro()
        val autores = autorController.findAllAutor()

        var adaptador= LibroAdapter(data,autores)
        rvLibro.layoutManager= LinearLayoutManager(this)
        rvLibro.adapter=adaptador


        bttnBuscar.setOnClickListener {
            val titulo= txtFiltro.text.toString()
            val libros = libroController.buscarLibroTitulo(titulo)
            adaptador = LibroAdapter(libros,autores)
            rvLibro.adapter = adaptador
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

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
    }

    fun irUsuarioMain(email:String){
        var ir = Intent(this,ListaLibroUsuarioActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(ir)
    }



    fun showToolbar(title: String?, upButton: Boolean) {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

}