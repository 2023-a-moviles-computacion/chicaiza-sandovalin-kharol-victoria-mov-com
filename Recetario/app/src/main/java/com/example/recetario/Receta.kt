package com.example.recetario
import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.Gson

class Receta(
    var id: Int,
    var foranea: Int?,
    var nombre: String?,
    var porciones: Int?,
    var calorias: Float?,
    var creacion: String?,
    var facil: Boolean?,
    var ingredientes: String?,
    var preparacion: String?
) {


    override fun toString(): String {
        return "Receta ${id} \nNombre: ${nombre} \nFecha de creación: ${creacion} \nPorciones: ${porciones} \nCalorías: ${calorias} \nFácil: ${facil} \nIngredientes: ${ingredientes} \nPreparación: ${preparacion}"
    }
}
