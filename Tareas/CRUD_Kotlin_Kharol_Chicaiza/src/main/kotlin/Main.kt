import com.google.gson.Gson
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import java.util.*
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    while (true) {
        println("Choose an option:")
        println("1. Manage Recetas")
        println("2. Manage Cocineros")
        println("0. Exit")

        when (scanner.nextInt()) {
            1 -> {
                println("Recetas Management:")
                println("1. Create Receta")
                println("2. Display Recetas")
                println("3. Update Receta")
                println("4. Delete Receta")

                when (scanner.nextInt()) {
                    1 -> {
                        println("Enter the Receta details:")
                        println("Nombre:")
                        val nombre = scanner.next()
                        println("Porciones:")
                        val porciones = scanner.nextInt()
                        println("Calorias:")
                        val calorias = scanner.nextFloat()
                        println("Fecha de creacion (yyyy-MM-dd):")
                        val fechaCreacion = Date(scanner.next())
                        println("Facil (true/false):")
                        val facil = scanner.nextBoolean()
                        println("Ingredientes (separated by comma):")
                        val ingredientes = scanner.next().split(",").toTypedArray()
                        println("Preparacion:")
                        val preparacion = scanner.next()

                        val receta = Receta(
                            Receta.idDisponible(),
                            nombre,
                            porciones,
                            calorias,
                            fechaCreacion,
                            facil,
                            ingredientes,
                            preparacion
                        )

                        Receta.crearReceta(receta)
                        println("Receta created successfully.")
                    }
                    2 -> {
                        val recetas = Receta.desplegarRecetas()
                        if (recetas.isNotEmpty()) {
                            println("Recetas:")
                            recetas.forEach { println(it) }
                        } else {
                            println("No recetas found.")
                        }
                    }
                    3 -> {
                        println("Enter the Receta ID to update:")
                        val id = scanner.nextInt()
                        val recetaToUpdate = Receta.desplegarRecetas().find { it.id == id }
                        if (recetaToUpdate != null) {
                            println("Enter the updated details for the Receta:")
                            println("Name:")
                            recetaToUpdate.nombre = scanner.next()
                            println("Porciones:")
                            recetaToUpdate.porciones = scanner.nextInt()
                            println("Calorias:")
                            recetaToUpdate.calorias = scanner.nextFloat()
                            println("Fecha de creación (yyyy-MM-dd):")
                            val creacionSF = scanner.next()
                            val formato = SimpleDateFormat("yyyy-MM-dd")
                            recetaToUpdate.creacion = formato.parse(creacionSF)
                            println("Es la receta facil (true/false):")
                            recetaToUpdate.facil = scanner.nextBoolean()
                            println("Numero de ingredientes:")
                            val numIngredientes = scanner.nextInt()
                            val ingredientes = mutableListOf<String>()
                            for (i in 1..numIngredientes) {
                                println("Ingrediente $i:")
                                val ingrediente = scanner.next()
                                ingredientes.add(ingrediente)
                            }
                            recetaToUpdate.ingredientes = ingredientes.toTypedArray()
                            println("Preparacion:")
                            recetaToUpdate.preparacion = scanner.next()

                            // Update the Receta
                            Receta.actualizarReceta(recetaToUpdate)
                            println("Receta updated successfully.")
                        } else {
                            println("Receta with ID $id not found.")
                        }
                    }
                    4 -> {
                        println("Enter the Receta ID to delete:")
                        val id = scanner.nextInt()
                        Receta.borrarReceta(id)
                        println("Receta deleted successfully.")
                    }
                    else -> {
                        println("Invalid option.")
                    }
                }
            }
            2 -> {
                println("Cocineros Management:")
                println("1. Create Cocinero")
                println("2. Display Cocineros")
                println("3. Update Cocinero")
                println("4. Delete Cocinero")

                when (scanner.nextInt()) {
                    1 -> {
                        println("Enter the Cocinero details:")
                        println("Nombre:")
                        val nombre = scanner.next()
                        println("Edad:")
                        val edad = scanner.nextInt()
                        println("Puntuación de clientes:")
                        val puntuacionClientes = scanner.nextFloat()
                        println("Fecha de integracion (yyyy-MM-dd):")
                        val fechaIntegracion = Date(scanner.next())
                        println("Autor de la receta (true/false):")
                        val autorReceta = scanner.nextBoolean()

                        // Prompt for Recetas
                        val recetas = mutableListOf<Receta>()
                        while (true) {
                            println("Do you want to add a Receta? (y/n)")
                            val choice = scanner.next()
                            if (choice.equals("y", ignoreCase = true)) {
                                println("Enter the Receta ID to add:")
                                val recetaId = scanner.nextInt()
                                val recetaToAdd = Receta.desplegarRecetas().find { it.id == recetaId }
                                if (recetaToAdd != null) {
                                    recetas.add(recetaToAdd)
                                    println("Receta added successfully.")
                                } else {
                                    println("Receta with ID $recetaId not found.")
                                }
                            } else {
                                break
                            }
                        }

                        val cocinero = Cocinero(
                            Cocinero.idDisponible(),
                            nombre,
                            edad,
                            puntuacionClientes,
                            fechaIntegracion,
                            autorReceta,
                            recetas.toTypedArray()
                        )

                        Cocinero.crearCocinero(cocinero)
                        println("Cocinero created successfully.")

                    }
                    2 -> {
                    val cocineros = Cocinero.desplegarCocinero()
                    if (cocineros.isNotEmpty()) {
                        println("Cocineros:")
                        cocineros.forEach { println(it) }
                    } else {
                        println("No cocineros found.")
                    }
                    }
                    3 -> {
                        println("Enter the Cocinero ID to update:")
                        val id = scanner.nextInt()
                        val cocineroToUpdate = Cocinero.desplegarCocinero().find { it.id == id }
                        if (cocineroToUpdate != null) {
                            println("Enter the updated details for the Cocinero:")
                            println("Nombre:")
                            cocineroToUpdate.nombre = scanner.next()
                            println("Edad:")
                            cocineroToUpdate.edad = scanner.nextInt()
                            println("Puntuación de clientes:")
                            cocineroToUpdate.costumersScore = scanner.nextFloat()
                            println("Fecha de integración (yyyy-MM-dd):")
                            val integracionSF = scanner.next()
                            val formato = SimpleDateFormat("yyyy-MM-dd")
                            cocineroToUpdate.fechaIntegracion = formato.parse(integracionSF)
                            println("Es el autor de la receta (true/false):")
                            cocineroToUpdate.autor = scanner.nextBoolean()
                            println("Número de recetas:")
                            val numRecetas = scanner.nextInt()
                            val recetas = mutableListOf<Receta>()
                            for (i in 1..numRecetas) {
                                println("Receta $i:")
                                val recetaId = scanner.nextInt()
                                val receta = Receta.desplegarRecetas().find { it.id == recetaId }
                                if (receta != null) {
                                    recetas.add(receta)
                                }
                            }
                            cocineroToUpdate.recetas = recetas.toTypedArray()

                            // Update the Cocinero
                            Cocinero.actualizarCocinero(cocineroToUpdate)
                            println("Cocinero updated successfully.")
                        } else {
                            println("Cocinero with ID $id not found.")
                        }
                    }
                    4 -> {
                        println("Enter the Cocinero ID to delete:")
                        val id = scanner.nextInt()
                        Cocinero.borrarCocinero(id)
                        println("Cocinero deleted successfully.")
                    }
                    else -> {
                        println("Invalid option.")
                    }
                }
            }
            0 -> {
                println("Exiting...")
                return
            }
            else -> {
                println("Invalid option.")
            }
        }
    }
}



data class Receta(
    val id: Int,
    var nombre: String,
    var porciones: Int,
    var calorias: Float,
    var creacion: Date,
    var facil: Boolean,
    var ingredientes: Array<String>,
    var preparacion: String
) {
    companion object {
        private const val archivo_recetas = "recetas.txt"

        fun desplegarRecetas(): List<Receta> {
            val archivoRecetas = File(archivo_recetas)
            val gson = Gson()
            val lineas = archivoRecetas.readLines()
            val recetas = mutableListOf<Receta>()

            for (linea in lineas) {
                val receta = gson.fromJson(linea, Receta::class.java)
                recetas.add(receta)
            }

            return recetas
        }

        fun crearReceta(receta: Receta) {
            val recetas = desplegarRecetas().toMutableList()
            recetas.add(receta)
            guardarReceta(recetas)
        }

        fun idDisponible(): Int {
            val recetas = desplegarRecetas()
            return recetas.map { it.id }.maxOrNull()?.plus(1) ?: 1
        }

        fun borrarReceta(id: Int) {
            val recetas = desplegarRecetas().toMutableList()
            val recetaBorrar = recetas.find { it.id == id }
            if (recetaBorrar != null) {
                recetas.remove(recetaBorrar)
                guardarReceta(recetas)
            }
        }

        fun guardarReceta(recetas: List<Receta>) {
            val archivoRecetas = File(archivo_recetas)
            val gson = Gson()
            val lineas = recetas.map { gson.toJson(it) }
            archivoRecetas.writeText(lineas.joinToString("\n"))
        }
        fun actualizarReceta(receta: Receta) {
            val recetas = Receta.desplegarRecetas().toMutableList()
            val index = recetas.indexOfFirst { it.id == receta.id }
            if (index != -1) {
                recetas[index] = receta
                Receta.guardarReceta(recetas)
            }
        }
    }

    override fun toString(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val fecha = formato.format(creacion)
        return """
            Receta #$id
            Nombre: $nombre
            Fecha de creación: $fecha
            Porciones: $porciones
            Calorías: $calorias
            Fácil: $facil
            Ingredientes: ${ingredientes.joinToString(", ")}
            Preparación: $preparacion
        """.trimIndent()
    }
}


data class Cocinero(
    val id: Int,
    var nombre: String,
    var edad: Int,
    var costumersScore: Float,
    var fechaIntegracion: Date,
    var autor: Boolean,
    var recetas: Array<Receta>
) {
    companion object {
        private const val archivo_cocineros = "cocineros.txt"

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

