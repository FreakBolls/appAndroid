package com.example.proyectoandroid.data

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyectoandroid.utils.AppConfig

class IniBd:SQLiteOpenHelper(AppConfig.CONTEXTO,AppConfig.NOMBRE_BD,null,AppConfig.VERSION){


    override fun onCreate(db: SQLiteDatabase) {
        //TABLA AUTOR
        db.execSQL( "create table tb_autor("+
                "idAutor integer primary key autoincrement,"+
                "nombreAutor varchar(35),"+
                "apellidoAutor varchar(35),"+
                "pais varchar(35),"+
                "fecha_nacimiento varchar(30),"+
                "imagenPerfil TEXT)")


        //TABLA LIBRO
        db.execSQL( "create table tb_libro("+
                "idLibro integer primary key autoincrement,"+
                "tituloLibro varchar(50),"+
                "idAutor integer not null,"+
                "editorial varchar(35),"+
                "anio_publicacion integer,"+
                "genero varchar(50),"+
                "imagenLibro TEXT,"+
                "FOREIGN KEY (idAutor) REFERENCES tb_autor(idAutor))")
        //AGREGAR CANTIDAD

        //TABLA ALUMNO
        db.execSQL( "create table tb_alumno("+
                "idAlumno integer primary key autoincrement,"+
                "nombreAlumno varchar(40),"+
                "apellidoAlumno varchar(40),"+
                "fecha_nacimiento varchar(30),"+
                "direccion varchar(50),"+
                "telefono varchar(9),"+
                "email varchar(20) unique)")

        db.execSQL("insert into tb_autor values(null,'Autor','Anonimo','Perú','1927-06-03','p1.png')")
        //db.execSQL("insert into tb_autor values(null,'Miguel ','de Cervantes','Perú','1998-08-11')")
      //  db.execSQL("insert into tb_autor values(null,'Jose','Maria Arguedas','Perú','1998-08-11')")


        db.execSQL("insert into tb_libro values(null,'Odisea','2','Akal',1869,'Drama','q1.png')")
        db.execSQL("insert into tb_libro values(null,'El código Da Vinci','1','Akal',1857,'Drama','q2.png')")
        //db.execSQL("insert into tb_libro values(null,'Alicia en el país de las maravillas','3','Akal',1967,'Drama')")

        db.execSQL("insert into tb_alumno values(null,'Luis','Mora Luna','1998-08-11','Jr. JoseDel Piero 186','946152051','luismlu@gmail.com')")

    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}