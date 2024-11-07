package com.example.proyectoandroid.controlador

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.proyectoandroid.entidad.Autor
import com.example.proyectoandroid.utils.AppConfig


class AutorController {

    private var context= AppConfig.CONTEXTO.applicationContext
    //LISTADO DE AUTORES

    fun obteneridAutor():Int{
        val listaAutor = findAllAutor().size // Obtenemos la lista completa de libros
        return listaAutor
    }

    fun findAllAutor():ArrayList<Autor>{
        var lista=ArrayList<Autor>()
        var CN:SQLiteDatabase= AppConfig.BD.readableDatabase
        var sql="select * from tb_autor"
        var RS=CN.rawQuery(sql,null)
        while(RS.moveToNext()){
            var bean=Autor(RS.getInt(0),RS.getString(1),RS.getString(2),
                RS.getString(3),RS.getString(4),RS.getString(5))
            lista.add(bean)
        }
        return lista
    }

    //BUSCAR AUTOR POR ID
    fun findByIdAutor(cod:Int):Autor{
        lateinit var bean:Autor
        var CN:SQLiteDatabase= AppConfig.BD.readableDatabase
        var sql="select * from tb_autor where idAutor=?"
        var RS=CN.rawQuery(sql, arrayOf(cod.toString()))

        if(RS.moveToNext()){
            bean= Autor(RS.getInt(0),RS.getString(1),
                RS.getString(2), RS.getString(3),
                RS.getString(4),RS.getString(5))
        }
        return bean
    }


    //REGISTRAR AUTOR
    fun saveAutor(bean:Autor):Int{
        var salida = -1
        var CN=AppConfig.BD.writableDatabase
        var content=ContentValues()

        content.put("nombreAutor",bean.nombreAutor)
        content.put("apellidoAutor",bean.apellidoAutor)
        content.put("pais",bean.pais)
        content.put("fecha_nacimiento",bean.fecha_nacimiento)
        content.put("imagenPerfil",bean.imagenPerfilAutor)

        salida=CN.insert("tb_autor",null,content).toInt()
        CN.close()
        return salida;
    }

    //ACTUALIZAR AUTOR SEGUN ID
    fun updateAutor(bean:Autor):Int{
        var salida = -1;
        var CN=AppConfig.BD.writableDatabase
        var content=ContentValues()
        content.put("nombreAutor",bean.nombreAutor)
        content.put("apellidoAutor",bean.apellidoAutor)
        content.put("pais",bean.pais)
        content.put("fecha_nacimiento",bean.fecha_nacimiento)
        content.put("imagenPerfil",bean.imagenPerfilAutor)
        salida=CN.update("tb_autor",content,"idAutor=?", arrayOf(bean.idAutor.toString()))

        return salida;
    }

    //ELIMINAR AUTOR
    fun deleteByIdAutor(cod: Int):Int{
        var salida = -1;
        var CN=AppConfig.BD.writableDatabase
        salida=CN.delete("tb_autor","idAutor=?", arrayOf(cod.toString()))
        return salida;
    }
    //OBTENER EL NOMBRE COMPLETO DE LA BASE DE DATOS PARA LLENAR EL SPINNER AUTOR
    fun obtenerAutorPorNombreCompleto(nombreCompleto: String): Autor? {
        val listaAutores = findAllAutor()
        return listaAutores.find { autor ->
            "${autor.nombreAutor} ${autor.apellidoAutor}" == nombreCompleto
        }
    }


    //BUSCAR AUTOR POR APELLIDO
    fun buscarAutor(nombreOape: String): List<Autor> {
        val autores = mutableListOf<Autor>()
        val db = AppConfig.BD.readableDatabase
        val sql: String
        val selectionArgs: Array<String>?

        if (nombreOape.isEmpty()) {
            sql = "SELECT * FROM tb_autor"
            selectionArgs = null
        } else {
            sql = "SELECT * FROM tb_autor WHERE nombreAutor LIKE ? OR apellidoAutor LIKE ?"
            selectionArgs = arrayOf("%$nombreOape%", "%$nombreOape%")
        }

        val cursor = db.rawQuery(sql, selectionArgs)
        while (cursor.moveToNext()) {
            val obj = Autor(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
            )
            autores.add(obj)
        }
        cursor.close()
        db.close()
        return autores
    }
}