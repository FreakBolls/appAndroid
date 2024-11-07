package com.example.proyectoandroid

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoandroid.controlador.AutorController
import com.example.proyectoandroid.controlador.LibroController
import com.example.proyectoandroid.data.IniBd
import com.example.proyectoandroid.entidad.Libro
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class RegistrarLibroActivity:AppCompatActivity() {

    private lateinit var txtTitulo:TextInputEditText
    private lateinit var spnAutor:AutoCompleteTextView
    private lateinit var txtEditorial:TextInputEditText
    private lateinit var txtAnoPublicacion:TextInputEditText
    private lateinit var spnGenero:AutoCompleteTextView


    private lateinit var bttnVolverListadoLibro:Button
    private lateinit var bttnRegistrar:Button
    private lateinit var BD: IniBd


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registrar_libro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtTitulo = findViewById(R.id.txtTituloLibro)
        spnAutor = findViewById(R.id.spnAutor)
        txtEditorial =findViewById(R.id.txtEditorial)
        txtAnoPublicacion=findViewById(R.id.txtAnioDePublicacion)
        spnGenero = findViewById(R.id.spnGenero)
        bttnVolverListadoLibro = findViewById(R.id.bttnVolverListadoLibro)


        BD = IniBd()
        mostrarAutores()
        bttnRegistrar = findViewById(R.id.bttnRegistrarLibro)

        bttnRegistrar.setOnClickListener{registrarLibro()}
        bttnVolverListadoLibro.setOnClickListener{VolverListadoAutor()}
    }

    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    fun VolverListadoAutor(){
        var regresar =Intent(this,ListaLibroActivity::class.java)
        startActivity(regresar)
    }
    //VALIDAR CAMPOS

    private fun validarCampos(): Boolean {
        val titulo = txtTitulo.text.toString().trim()
        val autor = spnAutor.text.toString().trim()
        val editorial = txtEditorial.text.toString().trim()
        val anopublicacion =txtAnoPublicacion.text.toString().trim()
        val genero =spnGenero.text.toString().trim()

        if (titulo.isEmpty()) {
            txtTitulo.error = "El titulo es requerido"
            txtTitulo.requestFocus()
            return false
        }

        if (autor.isEmpty()) {
            spnAutor.error = "El autor es requerido"
            return false
        }

        if (editorial.isEmpty()) {
            txtEditorial.error = "La Editorial es requerida"
            txtEditorial.requestFocus()
            return false
        }

        if (anopublicacion.isEmpty()) {
            txtAnoPublicacion.error = "El año de publicación"
            txtAnoPublicacion.requestFocus()
            return false
        }

        if (!anopublicacion.matches(Regex("\\d{4}"))) {
            txtAnoPublicacion.error = "Ingrese un año válido"
            txtAnoPublicacion.requestFocus()
            return false
        }

        val anio_publicacion = anopublicacion.toInt()

        val anioActual = Calendar.getInstance().get(Calendar.YEAR)

        if (anio_publicacion < 1800 || anio_publicacion > anioActual) {
            txtAnoPublicacion.error = "El año de publicación debe estar entre 1800 y $anioActual"
            txtAnoPublicacion.requestFocus()
            return false
        }
        if (genero.isEmpty()) {
            spnGenero.error = "El genero es requerido"
            return false
        }

        return true
    }


    //REGISTRAR LIBRO

    fun mostrarAutores(){
        val db = BD.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT nombreAutor, apellidoAutor FROM tb_autor", null)
        val autores = mutableListOf<String>()
        try {
            with(cursor) {
                while (moveToNext()) {
                    val nombre = getString(getColumnIndexOrThrow("nombreAutor"))
                    val apellido = getString(getColumnIndexOrThrow("apellidoAutor"))
                    val NombreCompleto = "$nombre $apellido"
                    autores.add(NombreCompleto)
                }
            }
        } finally {
            cursor.close()
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, autores)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnAutor.setAdapter(adapter)
    }


    private fun obtenerIdAutor(nombreCompletoAutor: String): Int {

        val autor = AutorController().obtenerAutorPorNombreCompleto(nombreCompletoAutor)
        return autor?.idAutor ?: -1
    }

    private fun obtenerIdLibro(): Int {
        val idlibro = LibroController().obteneridLibro()
        // Retorna el ID del libro si lo encuentra, o -1 si no encuentra el libro
        return idlibro ?: -1
    }


    fun registrarLibro(){
        if (validarCampos()) {
            val tituloLibro = txtTitulo.text.toString().trim()
            val nombreAutor = spnAutor.text.toString().trim()
            val editorial = txtEditorial.text.toString().trim()
            val anio_publicacion = txtAnoPublicacion.text.toString().trim().toInt()
            val genero = spnGenero.text.toString().trim()
            val imagen = "q"+obtenerIdLibro().toString()

            val idAutor = obtenerIdAutor(nombreAutor)

            val bean = Libro(0, tituloLibro, idAutor, editorial, anio_publicacion,genero,imagen)

            val salida = LibroController().saveLibro(bean)

            if (salida > 0) {
                showAlert("Libro registrado correctamente")
                val datos = Intent(this, ListaLibroActivity::class.java)
                startActivity(datos)

            } else {
                showAlert("Error en el registro del Libro")
            }
        }
    }




}
