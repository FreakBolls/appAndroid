package com.example.proyectoandroid

import android.content.DialogInterface
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

class ActualizarEliminarAutorActivity:AppCompatActivity() {

        private lateinit var txtidAutor:TextInputEditText
        private lateinit var txtNombreAutorUpdate:TextInputEditText
        private lateinit var txtApellidoAutorUpdate:TextInputEditText
        private lateinit var spnPaisUpdate:AutoCompleteTextView
        private lateinit var txtFechaNacimientoUpdate:TextInputEditText
        private lateinit var bttnActualizarAutor:Button
        private lateinit var bttnEliminarAutor:Button
        private lateinit var bttnVolverListadoAutorActualizar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.actualizar_eliminar_autor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtidAutor = findViewById(R.id.txtidAutor)
        txtNombreAutorUpdate = findViewById(R.id.txtNombreAutorActualizar)
        txtApellidoAutorUpdate = findViewById(R.id.txtApellidoAutorActualizar)
        spnPaisUpdate = findViewById(R.id.spnPaisUpdate)
        txtFechaNacimientoUpdate =findViewById(R.id.txtFechaNacimientoActualizar)
        bttnActualizarAutor =findViewById(R.id.bttnActualizarAutor)
        bttnEliminarAutor =findViewById(R.id.bttnEliminarAutor)
        bttnVolverListadoAutorActualizar =findViewById(R.id.bttnVolverListadoAutorActualizar)
        txtFechaNacimientoUpdate.setOnClickListener{MostrarCalendario()}

        bttnActualizarAutor.setOnClickListener{ActualizarAutor()}
        datos()
        bttnEliminarAutor.setOnClickListener{eliminarAutor()}
        bttnVolverListadoAutorActualizar.setOnClickListener(){regresarListadoAutor()}
    }

    fun regresarListadoAutor(){
        var regresar = Intent(this,ListaAutorActivity::class.java)
        startActivity(regresar)
    }

    private fun MostrarCalendario(){
        val datePicker= DatePickerFragment{day,month,year->onDateSelected(day,month,year)}
        datePicker.show(supportFragmentManager,"datePicker")
    }
    fun onDateSelected(day:Int,month:Int,year:Int){
        txtFechaNacimientoUpdate.setText("$day/$month/$year")
    }

    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }


    private fun obtenerIdAutor(): Int {
        val idAutor = AutorController().obteneridAutor()

        // Retorna el ID del libro si lo encuentra, o -1 si no encuentra el libro
        return idAutor ?: -1
    }

    fun ActualizarAutor(){
        if (validarCampos()) {
            var Codigo = txtidAutor.text.toString().toInt()
            var Nombre = txtNombreAutorUpdate.text.toString()
            var Apellido = txtApellidoAutorUpdate.text.toString()
            var Pais = spnPaisUpdate.text.toString()
            var FechaNacimiento = txtFechaNacimientoUpdate.text.toString()
            val imagen = 'a'+obtenerIdAutor().toString()

            var bean = Autor(Codigo, Nombre, Apellido, Pais, FechaNacimiento,imagen)

            var salida = AutorController().updateAutor(bean)

            if (salida > 0) {
                showAlert("Autor actualizado correctamente")
                regresarListadoAutor()
            } else
                showAlert("Error en el actualizar Autor")
        }
    }

    fun datos(){
        var info=intent.extras!!
        var bean= AutorController().findByIdAutor(info.getInt("idAutor"))
        txtidAutor.setText(bean.idAutor.toString())
        txtNombreAutorUpdate.setText(bean.nombreAutor)
        txtApellidoAutorUpdate.setText(bean.apellidoAutor)
        spnPaisUpdate.setText(bean.pais,false)
        txtFechaNacimientoUpdate.setText(bean.fecha_nacimiento)
    }


    fun showAlertEliminar(men:String,cod:Int){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",DialogInterface.OnClickListener{
                dialogInterface: DialogInterface, i: Int ->
            var salida =AutorController().deleteByIdAutor(cod)
            if (salida>0) {
                showAlert("Autor eliminado correctamente")
                regresarListadoAutor()
            }
            else
                showAlert("Error al eliminar Autor")
        })
        builder.setNegativeButton("Cancelar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }

    fun eliminarAutor(){
        var cod = txtidAutor.text.toString().toInt()
        showAlertEliminar("Desea eliminar el Autor : "+cod,cod)
    }

    private fun validarCampos(): Boolean {
        val nombre = txtNombreAutorUpdate.text.toString().trim()
        val apellido = txtApellidoAutorUpdate.text.toString().trim()
        val pais = spnPaisUpdate.text.toString().trim()
        val fechaNacimiento =txtFechaNacimientoUpdate.text.toString().trim()

        if (nombre.isEmpty()) {
            txtNombreAutorUpdate.error = "El nombre es requerido"
            txtNombreAutorUpdate.requestFocus()
            return false
        }

        if (apellido.isEmpty()) {
            txtApellidoAutorUpdate.error = "El apellido es requerido"
            txtApellidoAutorUpdate.requestFocus()
            return false
        }

        if (pais.isEmpty()) {
            spnPaisUpdate.error = "La nacionalidad es requerida"
            spnPaisUpdate.requestFocus()
            return false
        }

        if (fechaNacimiento.isEmpty()) {
            txtFechaNacimientoUpdate.error = "La fecha de nacimiento es requerida"
            return false
        }

        return true
    }


}