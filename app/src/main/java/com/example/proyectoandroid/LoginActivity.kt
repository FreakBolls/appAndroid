package com.example.proyectoandroid

import android.content.ContentValues.TAG
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectoandroid.databinding.LoginMainBinding

import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class LoginActivity : AppCompatActivity() {

    private lateinit var txtUsuario: TextInputEditText
    private lateinit var txtPassword: TextInputEditText
    private lateinit var tvRegistrarse: TextView
    private lateinit var btnIngresar: Button
    private lateinit var btnRegistrarse: Button


    private lateinit var binding:LoginMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val analytics=FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","Integracion de firebase completa")
        analytics.logEvent("Init",bundle)

        binding=LoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth=Firebase.auth

        txtUsuario=findViewById(R.id.txtCorreo)
        txtPassword=findViewById(R.id.txtPassword)
        tvRegistrarse=findViewById(R.id.tvRegistrarse)
        btnIngresar=findViewById(R.id.btnIngresar)
        btnRegistrarse=findViewById(R.id.btnRegistrar)

        binding.btnRegistrar.setOnClickListener{
            if(binding.txtCorreo.getText().toString().trim().isNotEmpty() && binding.txtPassword.getText().toString().trim().isNotEmpty()) {

                auth.createUserWithEmailAndPassword(binding.txtCorreo.getText().toString().trim(),
                binding.txtPassword.getText().toString().trim()).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        showAlert("Usuario autenticado correctamente")
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "auth failed", Toast.LENGTH_SHORT).show()

                    }
                }
            }else
                ir()
        }

        binding.btnIngresar.setOnClickListener{
            if(binding.txtCorreo.getText().toString().trim().isNotEmpty() && binding.txtPassword.getText().toString().trim().isNotEmpty()) {
                var corre=  binding.txtCorreo.getText().toString().trim()
                var pass=binding.txtPassword.getText().toString().trim()

                Log.d("Correo",corre)

                auth.signInWithEmailAndPassword(
                    binding.txtCorreo.getText().toString().trim(),
                    binding.txtPassword.getText().toString().trim()
                ).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithEmail:success")
                        if(corre.equals("administrador@biblioteca.com") && pass.equals("biblioteca12?")) {
                            val intent = Intent(this, MainActivity::class.java)
                            showHome(corre)
                            startActivity(intent)
                        }else{
                            showHome2(corre)
                            Log.d("UsuarioMainActivity","UsuarioMainActivity")
                        }
                    } else {
                        Log.w(TAG, "singInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "auth failed", Toast.LENGTH_SHORT).show()
                        ir()
                    }
                }
            }else showAlertError()
        }
    }




    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Información de logueo")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    fun showAlertError(){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }

    fun showHome(email:String){
        val homeIntent=Intent(this,MainActivity::class.java).apply {
            putExtra("email",email)

        }
        startActivity(homeIntent)
    }

    fun showHome2(email:String){
        val homeIntent=Intent(this,UsuarioMainActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(homeIntent)
    }

    fun ir(){
        val homeIntent=Intent(this,LoginActivity::class.java)
        startActivity(homeIntent)
    }

}