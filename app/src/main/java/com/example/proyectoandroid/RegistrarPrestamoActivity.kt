package com.example.proyectoandroid

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoandroid.services.ApiServicePrestamo
import com.example.proyectoandroid.data.IniBd
import com.example.proyectoandroid.entidad.Libro
import com.example.proyectoandroid.entidad.Prestamo

import com.example.proyectoandroid.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date


class RegistrarPrestamoActivity:AppCompatActivity() {

    private lateinit var BD: IniBd
    private lateinit var spnLibro: AutoCompleteTextView
    private lateinit var txtFechaPrestamo: TextInputEditText
    private lateinit var txtFechaDevolucion: TextInputEditText
    private lateinit var bttnRegistrar: Button
    private lateinit var bttnVolverListadoPrestamo: Button
    private  lateinit var  api: ApiServicePrestamo

    private lateinit var libros: List<Libro>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registrar_prestamo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        spnLibro = findViewById(R.id.spnLibro)

        bttnRegistrar = findViewById(R.id.bttnRegistrarPrestamo)
        bttnVolverListadoPrestamo = findViewById(R.id.bttnVolverListadoPrestamo)


        api = ApiUtils.getApiPrestamo()
        BD = IniBd()

        mostrarLibros()

        bttnRegistrar.setOnClickListener{registrarPrestamo()}

    }


    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }


    fun mostrarLibros(){
        val db = BD.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM tb_libro", null)
        libros = mutableListOf()
        try {
            with(cursor) {
                while (moveToNext()) {
                    val idLibro = getInt(getColumnIndexOrThrow("idLibro"))
                    val tituloLibro = getString(getColumnIndexOrThrow("tituloLibro"))
                    val idAutor = getInt(getColumnIndexOrThrow("idAutor"))
                    val editorial = getString(getColumnIndexOrThrow("editorial"))
                    val anio_publicacion = getInt(getColumnIndexOrThrow("anio_publicacion"))
                    val genero = getString(getColumnIndexOrThrow("genero"))
                    val imagen = getString(getColumnIndexOrThrow("imagenLibro"))

                    val libro = Libro(idLibro, tituloLibro, idAutor, editorial, anio_publicacion, genero,
                        imagen
                    )
                    (libros as MutableList<Libro>).add(libro)
                }
            }
        } finally {
            cursor.close()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, libros.map { it.tituloLibro })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnLibro.setAdapter(adapter)
    }

    fun registrarPrestamo(){
        var selectedTitulo = spnLibro.text.toString()
        val selectedLibro = libros.find { it.tituloLibro == selectedTitulo }

        val idLibro = selectedLibro?.idLibro ?: -1


        val fechaPrestamo = "2024-10-25"
        val fechaDevolucion = "2024-11-01"

        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d("Usuario", "Usuario actual: $currentUser")
        val userEmail = currentUser?.email ?: currentUser.toString()

        var bean=Prestamo(0,idLibro,userEmail,fechaPrestamo,fechaDevolucion,0)

        api.save(bean).enqueue(object: Callback<Prestamo> {
            override fun onResponse(call: Call<Prestamo>, response: Response<Prestamo>) {
                if (response.isSuccessful) {
                    val obj = response.body()!!
                    showAlert("Prestamo registrado")
                    if (userEmail.equals("administrador@biblioteca.com")) {
                        irListadoPrestamo()
                    }else {
                        irMenuUsuario()
                    }

                } else {
                    // Obtener el mensaje de error del cuerpo de la respuesta
                    val errorBody = response.errorBody()?.string()
                    Log.d("Error 400", "Código: ${response.code()}, Mensaje: $errorBody")
                    showAlert("Error al registrar: ${response.code()}. Mensaje: $errorBody")
                }
            }

            override fun onFailure(call: Call<Prestamo>, t: Throwable) {
                Log.d("Error", "Error de conexión: ${t.localizedMessage}")
                showAlert(t.localizedMessage)
            }
        })
    }

    fun irListadoPrestamo(){
        var intent = Intent(this,ListaPrestamoActivity::class.java)
        startActivity(intent)

    }
    fun irMenuUsuario(){
        var intent = Intent(this,UsuarioMainActivity::class.java)
        startActivity(intent)

    }

}
