package com.example.proyectoandroid.adaptador

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.R

class ViewAutor(item: View) : RecyclerView.ViewHolder(item) {


    var tvIdAutor: TextView
    var tvNombreAutor: TextView
    var tvApellidoAutor: TextView
    var tvPaisAutor: TextView
    var imgFoto: ImageView

    init {

        tvIdAutor=item.findViewById(R.id.tvIdAutor)
        tvNombreAutor=item.findViewById(R.id.tvNombreAutor)
        tvApellidoAutor=item.findViewById(R.id.tvApellidoAutor)
        tvPaisAutor=item.findViewById(R.id.tvPaisAutor)
        imgFoto=item.findViewById(R.id.imgFoto)
    }
}