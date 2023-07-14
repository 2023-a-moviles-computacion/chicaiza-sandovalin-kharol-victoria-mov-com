package com.example.recetario

import com.google.gson.Gson
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

data class Cocinero(
    val id: Int,
    var nombre: String?,
    var edad: Int,
    var costumersScore: Float,
    var fechaIntegracion: Date,
    var autor: Boolean,
    var recetas: ArrayList<Receta>
) {
    override fun toString(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val fecha = formato.format(fechaIntegracion)
        return "Cocinero #${id} \nNombre: ${nombre} \nEdad: ${edad} \nPuntuación de clientes: ${costumersScore}\nFecha de integración: ${fecha}\nAutor de la receta: ${autor}\nRecetas: ${recetas.toString()}\n"
    }
}
