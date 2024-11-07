package com.example.proyectoandroid.adaptador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.R

class ViewLibro(item: View) : RecyclerView.ViewHolder(item) {

    var tvIdLibro: TextView
    var tvTitulo: TextView
    var tvAutorLibro: TextView
    var tvEditorialLibro: TextView
    var tvGeneroLibro: TextView
    var ivLibro:ImageView

    init {
        tvIdLibro=item.findViewById(R.id.tvIdLibro)
        tvTitulo=item.findViewById(R.id.tvTitulo)
        tvAutorLibro=item.findViewById(R.id.tvAutorLibro)
        tvEditorialLibro=item.findViewById(R.id.tvEditorialLibro)
        tvGeneroLibro=item.findViewById(R.id.tvGeneroLibro)
        ivLibro=item.findViewById(R.id.ivLibro)

    }
}