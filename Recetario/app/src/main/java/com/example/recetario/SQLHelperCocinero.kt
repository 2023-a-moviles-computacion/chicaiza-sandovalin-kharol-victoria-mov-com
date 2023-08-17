package com.example.recetario

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.number.IntegerWidth
import android.util.Log

class SQLHelperCocinero(
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "moviles", // nombre BDD
    null,
    1
)  {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("Database", "Creating cocinero table")

        val scriptSQLCrearTablaCocinero =
            """
               CREATE TABLE COCINERO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                edad INT,
                costumersScore REAL,
                fechaIntegracion VARCHAR(50),
                autor BOOLEAN
            )
 
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCocinero)
        Log.d("Database", "created cocinero table")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}




    fun crearCocinero(
        nombre: String,
        edad: Int,
        costumersScore: Float,
        fechaIntegracion: String,
        autor: Boolean,

    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("edad", edad)
        valoresAGuardar.put("costumersScore", costumersScore)
        valoresAGuardar.put("fechaIntegracion", fechaIntegracion)
        valoresAGuardar.put("autor", autor)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "COCINERO", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }



    fun eliminarCocineroFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString() )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "COCINERO", // Nombre tabla
                "id=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }







    fun actualizarCocineroFormulario(
        nombre: String,
        edad: Int?,
        costumersScore: Float?,
        fechaIntegracion: String,
        autor: Boolean,
        id:Int,
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("edad", edad)
        valoresAActualizar.put("costumersScore", costumersScore)
        valoresAActualizar.put("fechaIntegracion", fechaIntegracion)
        valoresAActualizar.put("autor", autor)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "COCINERO", // Nombre tabla
                valoresAActualizar, // Valores
                "id=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }



    fun consultarCocineroPorID(id: Int): Cocinero{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM COCINERO WHERE ID = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )





        // logica busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = Cocinero(0, "" , 0,0.0f,"",false)
        val arreglo = arrayListOf<Cocinero>()
        if(existeUsuario){
            do{
                val id= resultadoConsultaLectura.getInt(0) // Indice 0
                val nombre = resultadoConsultaLectura.getString(1)
                val edad = resultadoConsultaLectura.getString(2)
                val costumersScore = resultadoConsultaLectura.getString(3)
                val fechaIntegracion = resultadoConsultaLectura.getString(4)
                val autor = resultadoConsultaLectura.getString(5)
                if(id != null){

                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.edad = edad.toInt()
                    usuarioEncontrado.costumersScore = costumersScore.toFloat()
                    usuarioEncontrado.fechaIntegracion = fechaIntegracion
                    usuarioEncontrado.autor = autor.toBoolean()

                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun retrieveAllCocineros(): ArrayList<Cocinero> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM COCINERO
    """.trimIndent()

        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            null // No parameters needed for all items
        )

        val cocineroList = ArrayList<Cocinero>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val edad = resultadoConsultaLectura.getInt(2)
                val costumersScore = resultadoConsultaLectura.getFloat(3)
                val fechaIntegracion = resultadoConsultaLectura.getString(4)
                val autor = resultadoConsultaLectura.getInt(5) != 0

                val cocinero = Cocinero(id, nombre, edad, costumersScore, fechaIntegracion, autor)
                cocineroList.add(cocinero)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return cocineroList
    }


}