package com.example.recetario

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLHelperRecetas(
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "tarea", // nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {

        Log.d("Database", "Creating RECETA table")
        val scriptSQLCrearTablaReceta =
            """
            CREATE TABLE RECETA (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            foranea INT,
            nombre VARCHAR(50),
            porciones INT,
            calorias REAL,
            creacion VARCHAR(50),
            facil BOOLEAN,
            ingredientes VARCHAR(50), -- Storing an array of strings as JSON or delimited text
            preparacion VARCHAR(100)
        )

            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaReceta)
        Log.d("Database", "RECETA table created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}




    fun crearReceta(
        foranea: Int,
        nombre: String,
        porciones: Int,
        calorias: Float,
        creacion: String,
        facil: Boolean,
        ingredientes: String,
         preparacion: String,
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("porciones", porciones)
        valoresAGuardar.put("calorias", calorias)
        valoresAGuardar.put("creacion", creacion)
        valoresAGuardar.put("facil", facil)
        valoresAGuardar.put("ingredientes", ingredientes)
        valoresAGuardar.put("preparacion", preparacion)
        valoresAGuardar.put("foranea", foranea)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "RECETA", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }



    fun eliminarRecetaFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString() )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "RECETA", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }







    fun actualizarRecetaFormulario(
        foranea: Int,
        nombre: String,
        porciones: Int,
        calorias: Float,
        creacion: String,
        facil: Boolean,
        ingredientes: String,
        preparacion: String,
        id:Int,
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("foranea", foranea)
        valoresAActualizar.put("porciones", porciones)
        valoresAActualizar.put("calorias", calorias)
        valoresAActualizar.put("creacion", creacion)
        valoresAActualizar.put("facil", facil)
        valoresAActualizar.put("ingredientes", ingredientes)
        valoresAActualizar.put("preparacion", preparacion)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "RECETA", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }



    fun consultarRecetaPorID(id: Int): Receta{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM RECETA WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )





        // logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = Receta(0, 0 , "",0,0.0f,"",false,"","")
        val arreglo = arrayListOf<Receta>()
        if(existeUsuario){
            do{
                var id= resultadoConsultaLectura.getInt(0) // Indice 0
                val foranea = resultadoConsultaLectura.getString(1)
                val nombre= resultadoConsultaLectura.getString(2)
                val porciones= resultadoConsultaLectura.getString(3)
                val calorias= resultadoConsultaLectura.getString(4)
                val creacion= resultadoConsultaLectura.getString(5)
                val facil= resultadoConsultaLectura.getString(6)
                val ingredientes= resultadoConsultaLectura.getString(7)
                val preparacion= resultadoConsultaLectura.getString(8)
                if(id != null){
                    // llenar el arreglo con un nuevo BEntrenador
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.foranea = foranea.toInt()
                    usuarioEncontrado.porciones = porciones.toInt()
                    usuarioEncontrado.calorias = calorias.toFloat()
                    usuarioEncontrado.creacion = creacion
                    usuarioEncontrado.facil = facil.toBoolean()
                    usuarioEncontrado.ingredientes = ingredientes
                    usuarioEncontrado.preparacion = preparacion

                }
            } while (resultadoConsultaLectura.moveToNext())
        }


        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun consultarRecetasPorForanea(foraneaValue: Int): ArrayList<Receta> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM RECETA WHERE FORANEA = ?
    """.trimIndent()
        val parametrosConsultaLectura = arrayOf(foraneaValue.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsultaLectura,
        )

        val recetaList = ArrayList<Receta>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val foranea = resultadoConsultaLectura.getInt(1)
                val nombre = resultadoConsultaLectura.getString(2)
                val porciones = resultadoConsultaLectura.getInt(3)
                val calorias = resultadoConsultaLectura.getFloat(4)
                val creacion = resultadoConsultaLectura.getString(5)
                val facil = resultadoConsultaLectura.getInt(6) != 0
                val ingredientes = resultadoConsultaLectura.getString(7)
                val preparacion = resultadoConsultaLectura.getString(8)

                val receta = Receta(id, foranea, nombre, porciones, calorias, creacion, facil, ingredientes, preparacion)
                recetaList.add(receta)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return recetaList
    }

}