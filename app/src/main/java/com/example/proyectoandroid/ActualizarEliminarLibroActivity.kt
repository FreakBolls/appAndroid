package com.example.proyectoandroid

import android.annotation.SuppressLint
import android.content.DialogInterface
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
import com.example.proyectoandroid.entidad.Autor
import com.example.proyectoandroid.entidad.Libro
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import java.util.concurrent.BlockingDeque

class ActualizarEliminarLibroActivity:AppCompatActivity() {

        private lateinit var txtCodigoLibro:TextInputEditText
        private lateinit var txtTituloUpdate:TextInputEditText
        private lateinit var spnAutorUpdate:AutoCompleteTextView
        private lateinit var txtEditorialUpdate:TextInputEditText
        private lateinit var txtPublicacionUpdate:TextInputEditText
        private lateinit var spnGeneroUpdate:AutoCompleteTextView
        private lateinit var bttnActualizarLibro:Button
        private lateinit var bttnEliminarLibro:Button
        private lateinit var bttnVolverListadoLibroActualizar:Button
        private lateinit var BD:IniBd

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.actualizar_eliminar_libro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtCodigoLibro = findViewById(R.id.txtidLibroUpdate)
        txtTituloUpdate = findViewById(R.id.txtTituloUpdate)
        spnAutorUpdate = findViewById(R.id.spnAutorUpdate)
        txtEditorialUpdate = findViewById(R.id.txtEditorialUpdate)
        txtPublicacionUpdate = findViewById(R.id.txtAnioPublicacionUpdate)
        spnGeneroUpdate =findViewById(R.id.spnGeneroUpdate)
        bttnActualizarLibro =findViewById(R.id.bttnActualizarLibro)
        bttnEliminarLibro =findViewById(R.id.bttnEliminarLibro)
        bttnVolverListadoLibroActualizar =findViewById(R.id.bttnVolverListadoLibroActualizar)
        BD = IniBd()

        bttnActualizarLibro.setOnClickListener{ActualizarLibro()}
        bttnEliminarLibro.setOnClickListener{eliminarLibro()}
        bttnVolverListadoLibroActualizar.setOnClickListener{regresarListadoLibro()}


        mostrarAutores()
        datos()
    }

    fun regresarListadoLibro(){
        var regresar = Intent(this,ListaLibroActivity::class.java)
        startActivity(regresar)
    }

    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    private fun obtenerIdLibro(): Int {
        val idlibro = LibroController().obteneridLibro()

        // Retorna el ID del libro si lo encuentra, o -1 si no encuentra el libro
        return idlibro ?: -1
    }


    fun ActualizarLibro(){
        if (validarCampos()) {
            var Codigo = txtCodigoLibro.text.toString().toInt()
            var Titulo = txtTituloUpdate.text.toString()
            val autorNombre = spnAutorUpdate.text.toString()
            val autorId = getIdAutor(autorNombre)
            var Editorial = txtEditorialUpdate.text.toString()
            var Publicacion = txtPublicacionUpdate.text.toString().toInt()
            var Genero = spnGeneroUpdate.text.toString()
            val imagen = 'p'+obtenerIdLibro().toString()


            var bean = Libro(Codigo, Titulo, autorId, Editorial, Publicacion,Genero,imagen)

            var salida = LibroController().updateLibro(bean)

            if (salida > 0) {
                showAlert("Libro actualizado correctamente")
                regresarListadoLibro()
            } else
                showAlert("Error en el actualizar Libro")
        }
    }

    private fun getIdAutor(nombreAutor: String): Int {
        val db = BD.readableDatabase
        val cursor = db.rawQuery("SELECT idAutor FROM tb_autor WHERE nombreAutor || ' ' || apellidoAutor = ?", arrayOf(nombreAutor))
        return if (cursor.moveToFirst()) {
            val idAutor = cursor.getInt(cursor.getColumnIndexOrThrow("idAutor"))
            cursor.close()
            idAutor
        } else {
            cursor.close()
            -1
        }
    }

    fun datos(){
        var info=intent.extras!!
        var bean= LibroController().findByIdLibro(info.getInt("idLibro"))
        txtCodigoLibro.setText(bean.idLibro.toString())
        txtTituloUpdate.setText(bean.tituloLibro)
        spnAutorUpdate.setText(getNombreAutor(bean.idAutor), false)
        txtEditorialUpdate.setText(bean.editorial)
        txtPublicacionUpdate.setText(bean.anio_publicacion.toString())
        spnGeneroUpdate.setText(bean.genero,false)
    }

    private fun getNombreAutor(idAutor: Int): String {
        val db = BD.readableDatabase
        val cursor = db.rawQuery("SELECT nombreAutor, apellidoAutor FROM tb_autor WHERE idAutor = ?", arrayOf(idAutor.toString()))
        return if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombreAutor"))
            val apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellidoAutor"))
            cursor.close()
            "$nombre $apellido"
        } else {
            cursor.close()
            "Desconocido"
        }
    }

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
        spnAutorUpdate.setAdapter(adapter)
    }

    //VALIDACIONES

    private fun validarCampos(): Boolean {
        val titulo = txtTituloUpdate.text.toString().trim()
        val autor = spnAutorUpdate.text.toString().trim()
        val editorial = txtEditorialUpdate.text.toString().trim()
        val anopublicacion =txtPublicacionUpdate.text.toString().trim()
        val genero =spnGeneroUpdate.text.toString().trim()

        if (titulo.isEmpty()) {
            txtTituloUpdate.error = "El titulo es requerido"
            txtTituloUpdate.requestFocus()
            return false
        }

        if (autor.isEmpty()) {
            spnAutorUpdate.error = "El autor es requerido"
            return false
        }

        if (editorial.isEmpty()) {
            txtEditorialUpdate.error = "La Editorial es requerida"
            txtEditorialUpdate.requestFocus()
            return false
        }

        if (anopublicacion.isEmpty()) {
            txtPublicacionUpdate.error = "El año de publicación"
            txtPublicacionUpdate.requestFocus()
            return false
        }

        if (!anopublicacion.matches(Regex("\\d{4}"))) {
            txtPublicacionUpdate.error = "Ingrese un año válido"
            txtPublicacionUpdate.requestFocus()
            return false
        }

        val anio_publicacion = anopublicacion.toInt()

        val anioActual = Calendar.getInstance().get(Calendar.YEAR)

        if (anio_publicacion < 1800 || anio_publicacion > anioActual) {
            txtPublicacionUpdate.error = "El año de publicación debe estar entre 1800 y $anioActual"
            txtPublicacionUpdate.requestFocus()
            return false
        }
        if (genero.isEmpty()) {
            spnGeneroUpdate.error = "El genero es requerido"
            return false
        }

        return true
    }

    //ELIMINAR

    fun showAlertEliminar(men:String,cod:Int){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",DialogInterface.OnClickListener{
                dialogInterface: DialogInterface, i: Int ->
            var salida =LibroController().deleteByIdLibro(cod)
            if (salida>0) {
                showAlert("Libro eliminado correctamente")
                regresarListadoLibro()
            }
            else
                showAlert("Error al eliminar Libro")
        })
        builder.setNegativeButton("Cancelar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }

    fun eliminarLibro(){
        var cod = txtCodigoLibro.text.toString().toInt()
        showAlertEliminar("Desea eliminar el Libro : "+cod,cod)
    }

}