package com.example.recetario
import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.Gson

class Receta(
    val id: Int,
    val nombre: String?,
    val porciones: Int,
    val calorias: Float,
    val creacion: Date,
    val facil: Boolean,
    val ingredientes: Array<String>,
    val preparacion: String
) {


    override fun toString(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val fecha = formato.format(creacion)
        return "Receta ${id} \nNombre: ${nombre} \nFecha de creación: ${fecha} \nPorciones: ${porciones} \nCalorías: ${calorias} \nFácil: ${facil} \nIngredientes: ${ingredientes.joinToString(", ")} \nPreparación: ${preparacion}"
    }
}
