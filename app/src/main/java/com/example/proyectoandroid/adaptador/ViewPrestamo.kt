package com.example.proyectoandroid.adaptador

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.R

class ViewPrestamo(item: View) : RecyclerView.ViewHolder(item) {

    var tvIdPrestamo: TextView
    var tvLibro: TextView
    var tvUsuario: TextView
    var tvFechaPrestamo: TextView
    var tvFechaDevolucion: TextView
    var tvEstado:TextView

    init {
        tvIdPrestamo=item.findViewById(R.id.tvIdPrestamo)
        tvLibro=item.findViewById(R.id.tvLibro)
        tvUsuario=item.findViewById(R.id.tvUsuario)
        tvFechaPrestamo=item.findViewById(R.id.tvFechaPrestamo)
        tvFechaDevolucion=item.findViewById(R.id.tvFechaDevolucion)
        tvEstado=item.findViewById(R.id.tvEstado)
    }
}