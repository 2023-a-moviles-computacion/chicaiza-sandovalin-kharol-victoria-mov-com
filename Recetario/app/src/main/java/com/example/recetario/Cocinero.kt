package com.example.recetario

import com.google.gson.Gson
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

data class Cocinero(
    var id: Int,
    var nombre: String?,
    var edad: Int?,
    var costumersScore: Float?,
    var fechaIntegracion: String?,
    var autor: Boolean?,

) {
    override fun toString(): String {

        return "Cocinero #${id} \nNombre: ${nombre} \nEdad: ${edad} \nPuntuación de clientes: ${costumersScore}\nFecha de integración: ${fechaIntegracion}\nAutor de la receta: ${autor}\n"
    }
}
