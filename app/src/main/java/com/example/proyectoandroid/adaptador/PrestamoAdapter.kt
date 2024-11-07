package com.example.proyectoandroid.adaptador


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.example.proyectoandroid.R

import com.example.proyectoandroid.entidad.Prestamo
import java.text.SimpleDateFormat


class PrestamoAdapter(var lista: List<Prestamo>):RecyclerView.Adapter<ViewPrestamo>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPrestamo {
        var vista=LayoutInflater.from(parent.context).
        inflate(R.layout.item_prestamo,parent,false)
        return ViewPrestamo(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
    override fun onBindViewHolder(holder: ViewPrestamo, position: Int) {

        holder.tvIdPrestamo.setText(lista.get(position).idPrestamo.toString())
        holder.tvLibro.setText(lista.get(position).idLibro.toString())
        holder.tvUsuario.setText(lista.get(position).idUsuario)
        holder.tvFechaPrestamo.setText(lista.get(position).fechaPrestamo)
        holder.tvFechaDevolucion.setText(lista.get(position).fechaDevolucion)

        holder.tvEstado.text = if (lista.get(position).estado == 1) "Activo" else "Inactivo"

        holder.tvEstado.setTextColor(
            if (lista.get(position).estado == 1) Color.BLUE else Color.RED
        )

        /* holder.itemView.setOnClickListener{
             var intent=Intent(AppConfig.CONTEXTO, ActualizarEliminarAutorActivity::class.java)
             intent.putExtra("idPrestamo",lista.get(position).idPrestamo)
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK);
             ContextCompat.startActivity(AppConfig.CONTEXTO,intent,null)
         }*/
        }


}
