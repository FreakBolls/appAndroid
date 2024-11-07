package com.example.proyectoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoandroid.controlador.AutorController
import com.example.proyectoandroid.controlador.LibroController
import com.example.proyectoandroid.entidad.Autor
import com.google.android.material.textfield.TextInputEditText

class RegistrarAutorActivity:AppCompatActivity() {
        private lateinit var txtNombreAutor:TextInputEditText
        private lateinit var txtApellidoAutor:TextInputEditText
        private lateinit var spnPais:AutoCompleteTextView
        private lateinit var txtFechaNacimiento:TextInputEditText
        private lateinit var bttnVolverListadoAutor:Button
        private lateinit var bttnRegistrar:Button



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registrar_autor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtNombreAutor= findViewById(R.id.txtNombreAutor)
        txtApellidoAutor =findViewById(R.id.txtApellidoAutor)
        spnPais=findViewById(R.id.spnPais)
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento)
        bttnVolverListadoAutor = findViewById(R.id.bttnVolverListadoAutor)
        bttnRegistrar = findViewById(R.id.bttnRegistrarAutor)

        //MOSTRAR CALENDARIO
        txtFechaNacimiento.setOnClickListener{MostrarCalendario()}

        bttnVolverListadoAutor.setOnClickListener{VolverListadoAutor()}
        bttnRegistrar.setOnClickListener{RegistrarAutor()}
    }


    private fun MostrarCalendario(){
        val datePicker= DatePickerFragment{day,month,year->onDateSelected(day,month,year)}
        datePicker.show(supportFragmentManager,"datePicker")
    }
    fun onDateSelected(day:Int,month:Int,year:Int){
        txtFechaNacimiento.setText("$day/$month/$year")
    }

    fun VolverListadoAutor(){
        var regresar =Intent(this,ListaAutorActivity::class.java)
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

    private fun validarCampos(): Boolean {
        val nombre = txtNombreAutor.text.toString().trim()
        val apellido = txtApellidoAutor.text.toString().trim()
        val pais = spnPais.text.toString().trim()
        val fechaNacimiento =txtFechaNacimiento.text.toString().trim()

        if (nombre.isEmpty()) {
            txtNombreAutor.error = "El nombre es requerido"
            txtNombreAutor.requestFocus()
            return false
        }

        if (apellido.isEmpty()) {
            txtApellidoAutor.error = "El apellido es requerido"
            txtApellidoAutor.requestFocus()
            return false
        }

        if (pais.isEmpty()) {
            spnPais.error = "El país es requerido"
            spnPais.requestFocus()
            return false
        }

        if (fechaNacimiento.isEmpty()) {
            txtFechaNacimiento.error = "La fecha de nacimiento es requerida"
            return false
        }

        return true
    }
    private fun obtenerIdAutor(): Int {
        val idautor = AutorController().obteneridAutor()

        // Retorna el ID del libro si lo encuentra, o -1 si no encuentra el libro
        return idautor ?: -1
    }

    fun RegistrarAutor(){
        if (validarCampos()) {
        val nombre = txtNombreAutor.text.toString().trim()
        val apellido = txtApellidoAutor.text.toString().trim()
        val pais = spnPais.text.toString().trim()
        val fechaNacimiento = txtFechaNacimiento.text.toString().trim()
        val imagen = 'p'+obtenerIdAutor().toString()

        val bean = Autor(0, nombre, apellido, pais, fechaNacimiento,imagen)

        val salida = AutorController().saveAutor(bean)

        if (salida > 0) {
            showAlert("Autor registrado correctamente")
            val datos = Intent(this, ListaAutorActivity::class.java)
            startActivity(datos)
        } else {
            showAlert("Error en el registro del Autor")
        }
    }
    }

}