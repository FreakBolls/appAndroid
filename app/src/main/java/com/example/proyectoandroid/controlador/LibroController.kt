package com.example.proyectoandroid.controlador

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.proyectoandroid.entidad.Libro
import com.example.proyectoandroid.utils.AppConfig

class LibroController {

    fun obteneridLibro():Int{
        val listaLibro = findAllLibro().size // Obtenemos la lista completa de libros
        return listaLibro
    }

    //LISTADO DE LIBROS
    fun findAllLibro():ArrayList<Libro>{
        var lista=ArrayList<Libro>()
        var CN: SQLiteDatabase = AppConfig.BD.readableDatabase
        var sql="select * from tb_libro"
        var RS=CN.rawQuery(sql,null)
        while(RS.moveToNext()){
            var bean= Libro(RS.getInt(0),RS.getString(1),RS.getInt(2),
                RS.getString(3),RS.getInt(4),RS.getString(5),RS.getString(6))
            lista.add(bean)
        }
        return lista
    }

    fun saveLibro(bean: Libro):Long{
        var salida = -1L
        var CN= AppConfig.BD.writableDatabase
        var content= ContentValues()

        content.put("tituloLibro",bean.tituloLibro)
        content.put("idAutor",bean.idAutor)
        content.put("editorial",bean.editorial)
        content.put("anio_publicacion",bean.anio_publicacion)
        content.put("genero",bean.genero)
        content.put("imagenPerfil",bean.imagenLibro)

        salida=CN.insert("tb_libro", null, content)
        CN.close()
        return salida
    }

    //BUSCAR LIBRO POR TITULO

    fun buscarLibroTitulo(titulo: String): List<Libro> {
        val libros = mutableListOf<Libro>()
        val db = AppConfig.BD.readableDatabase
        val sql:String
        val selectionArgs:Array<String>?

        if(titulo.isNullOrEmpty()){
            sql = "select * from tb_libro"
            selectionArgs= null
        }else   {
            sql= "select * from tb_libro where tituloLibro LIKE ?"
            selectionArgs= arrayOf("%$titulo%")
        }
        val cursor = db.rawQuery(sql,selectionArgs)
        while (cursor.moveToNext()) {
            val obj = Libro(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getString(5),
                cursor.getString(6)
            )
            libros.add(obj)
        }
        cursor.close()
        db.close()
        return libros
    }


    fun updateLibro(bean: Libro):Int{

        var salida = -1;
        var CN= AppConfig.BD.writableDatabase
        var content= ContentValues()

        content.put("tituloLibro",bean.tituloLibro)
        content.put("idAutor",bean.idAutor)
        content.put("editorial",bean.editorial)
        content.put("anio_publicacion",bean.anio_publicacion)
        content.put("genero",bean.genero)
        content.put("imagenPerfil",bean.imagenLibro)

        salida=CN.update("tb_libro",content,"idLibro=?", arrayOf(bean.idLibro.toString()))
        CN.close()
        return salida

    }


    fun findByIdLibro(cod:Int):Libro{
        lateinit var bean:Libro
        var CN:SQLiteDatabase= AppConfig.BD.readableDatabase
        var sql="select * from tb_libro where idLibro=?"
        var RS=CN.rawQuery(sql, arrayOf(cod.toString()))

        if(RS.moveToNext()){
            bean= Libro(RS.getInt(0),RS.getString(1),
                RS.getInt(2), RS.getString(3),
                RS.getInt(4),RS.getString(5),RS.getString(6))
        }
        return bean
    }

    //ELIMINAR LIBRO POR ID

    //ELIMINAR AUTOR
    fun deleteByIdLibro(cod: Int):Int{
        var salida = -1;
        var CN=AppConfig.BD.writableDatabase
        salida=CN.delete("tb_libro","idLibro=?", arrayOf(cod.toString()))
        return salida;
    }

}