package com.example.proyectoandroid.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.ActualizarEliminarAutorActivity
import com.example.proyectoandroid.R
import com.example.proyectoandroid.entidad.Autor
import com.example.proyectoandroid.utils.AppConfig

class AutorAdapter(var lista: List<Autor>):RecyclerView.Adapter<ViewAutor>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAutor {
        var vista=LayoutInflater.from(parent.context).
        inflate(R.layout.item_autor,parent,false)
        return ViewAutor(vista)

    }
    override fun getItemCount(): Int {
        return lista.size
    }
    override fun onBindViewHolder(holder: ViewAutor, position: Int) {
        val autor = lista[position]
        val imageName = "p${autor.idAutor}"

        val imageResource = AppConfig.CONTEXTO.resources.getIdentifier(
            imageName,
            "drawable",
            AppConfig.CONTEXTO.packageName
        )

        holder.tvIdAutor.setText(lista.get(position).idAutor.toString())
        holder.tvNombreAutor.setText(lista.get(position).nombreAutor)
        holder.tvApellidoAutor.setText(lista.get(position).apellidoAutor)
        holder.tvPaisAutor.setText(lista.get(position).pais)
        holder.imgFoto.setImageResource(imageResource)

        holder.itemView.setOnClickListener{
           var intent=Intent(AppConfig.CONTEXTO,ActualizarEliminarAutorActivity::class.java)
            intent.putExtra("idAutor",lista.get(position).idAutor)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK);
          ContextCompat.startActivity(AppConfig.CONTEXTO,intent,null)
        }
    }




}
