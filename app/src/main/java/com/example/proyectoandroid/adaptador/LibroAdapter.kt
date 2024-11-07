package com.example.proyectoandroid.adaptador

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectoandroid.ActualizarEliminarLibroActivity
import com.example.proyectoandroid.R
import com.example.proyectoandroid.entidad.Autor
import com.example.proyectoandroid.entidad.Libro
import com.example.proyectoandroid.utils.AppConfig

class LibroAdapter(var lista: List<Libro>,var listaAutores: List<Autor>):RecyclerView.Adapter<ViewLibro>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewLibro {
        var vista=LayoutInflater.from(parent.context).
        inflate(R.layout.item_libro,parent,false)
        return ViewLibro(vista)
    }
    override fun getItemCount(): Int {
        return lista.size
    }
    override fun onBindViewHolder(holder: ViewLibro, position: Int) {
        val libro = lista[position]

        val imageName = "q${libro.idLibro}"

        val imageResource = AppConfig.CONTEXTO.resources.getIdentifier(
            imageName,
            "drawable",
            AppConfig.CONTEXTO.packageName
        )
        holder.tvIdLibro.setText(lista.get(position).idLibro.toString())
        holder.tvTitulo.setText(lista.get(position).tituloLibro)
        holder.tvAutorLibro.setText(getNombreAutor(libro.idAutor))
        holder.tvEditorialLibro.setText(lista.get(position).editorial)
        holder.tvGeneroLibro.setText(lista.get(position).genero)
        holder.ivLibro.setImageResource(imageResource)

        holder.itemView.setOnClickListener{
            var intent= Intent(AppConfig.CONTEXTO, ActualizarEliminarLibroActivity::class.java)
            intent.putExtra("idLibro",libro.idLibro)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ContextCompat.startActivity(AppConfig.CONTEXTO,intent,null)
        }

        }
    private fun getNombreAutor(idAutor: Int): String {
        val autor = listaAutores.find { it.idAutor == idAutor }
        return if (autor != null) {
            "${autor.nombreAutor} ${autor.apellidoAutor}"
        } else {
            "Desconocido"
        }
    }

}
