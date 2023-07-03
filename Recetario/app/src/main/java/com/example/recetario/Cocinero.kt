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
    var recetas: Array<Receta>
) {
    companion object {
        private const val archivo_cocineros = "C:\\Users\\escritorio.virtual6\\Documents\\GitHub\\chicaiza-sandovalin-kharol-victoria-mov-com\\Tareas\\CRUD_Kotlin_Kharol_Chicaiza\\src\\main\\kotlin\\cocineros.txt"

        fun desplegarCocinero(): List<Cocinero> {
            val archivoCocineros = File(archivo_cocineros)
            val gson = Gson()
            val lineas = archivoCocineros.readLines()
            val cocineros = mutableListOf<Cocinero>()

            for (linea in lineas) {
                val cocinero = gson.fromJson(linea, Cocinero::class.java)
                cocineros.add(cocinero)
            }

            return cocineros
        }

        fun crearCocinero(cocinero: Cocinero) {
            val cocineros = desplegarCocinero().toMutableList()
            cocineros.add(cocinero)
            guardarCocinero(cocineros)
        }

        fun idDisponible(): Int {
            val cocineros = desplegarCocinero()
            return cocineros.map { it.id }.maxOrNull()?.plus(1) ?: 1
        }

        fun borrarCocinero(id: Int) {
            val cocineros = desplegarCocinero().toMutableList()
            val cocineroBorrar = cocineros.find { it.id == id }
            if (cocineroBorrar != null) {
                cocineros.remove(cocineroBorrar)
                guardarCocinero(cocineros)
            }
        }

        private fun guardarCocinero(cocineros: List<Cocinero>) {
            val archivoCocineros = File(archivo_cocineros)
            val gson = Gson()
            val lineas = cocineros.map { gson.toJson(it) }
            archivoCocineros.writeText(lineas.joinToString("\n"))
        }

        fun actualizarCocinero(cocinero: Cocinero) {
            val cocineros = desplegarCocinero().toMutableList()
            val index = cocineros.indexOfFirst { it.id == cocinero.id }
            if (index != -1) {
                cocineros[index] = cocinero
                guardarCocinero(cocineros)
            }
        }
    }

    override fun toString(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val fecha = formato.format(fechaIntegracion)
        return "Cocinero #$id \nNombre: $nombre \nEdad: $edad \nPuntuación de clientes: $costumersScore\nFecha de integración: $fecha\nAutor de la receta: $autor\nRecetas: ${recetas.contentToString()}\n"
    }
}
