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
        println("1. Gestionar Recetas")
        println("2. Gestionar Cocineros")
        println("0. Exit")

        when (scanner.nextInt()) {
            1 -> {
                println("Gestion de Recetas:")
                println("1. Ingresar Receta")
                println("2. Desplegar Recetas")
                println("3. Actualizar Receta")
                println("4. Borrar Receta")

                when (scanner.nextInt()) {
                    1 -> {
                        println("Ingrese los detalles de la Receta:")
                        println("Nombre:")
                        val nombre = readLine()
                        println("Porciones:")
                        val porciones = scanner.nextInt()
                        println("Calorias:")
                        val calorias = scanner.nextFloat()
                        println("Fecha de creacion (yyyy-MM-dd):")
                        val fechaCreacionString = readLine()
                        val formato = SimpleDateFormat("yyyy-MM-dd")
                        val fechaCreacion = formato.parse(fechaCreacionString)

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
                            println("recetas no encontradas.")
                        }
                    }
                    3 -> {
                        println("Recetas existentes:")
                        val recetasExistentes = Receta.desplegarRecetas()
                        recetasExistentes.forEach { println(it) }

                        println("\nIngrese el ID de la receta a actualizar:")
                        val id = readLine()!!.toInt()
                        val recetaToUpdate = recetasExistentes.find { it.id == id }

                        if (recetaToUpdate != null) {
                            println("Ingrese el atributo a actualizar (nombre, porciones, calorias, creacion, facil, ingredientes, preparacion):")
                            val atributo = readLine()

                            when (atributo) {
                                "nombre" -> {
                                    println("Nuevo nombre:")
                                    recetaToUpdate.nombre = readLine()!!
                                }
                                "porciones" -> {
                                    println("Nuevas porciones:")
                                    recetaToUpdate.porciones = readLine()!!.toInt()
                                }
                                "calorias" -> {
                                    println("Nuevas calorias:")
                                    recetaToUpdate.calorias = readLine()!!.toFloat()
                                }
                                "creacion" -> {
                                    println("Fecha de creación (yyyy-MM-dd):")
                                    val creacionSF = readLine()!!
                                    val formato = SimpleDateFormat("yyyy-MM-dd")
                                    recetaToUpdate.creacion = formato.parse(creacionSF)
                                }
                                "facil" -> {
                                    println("Es la receta facil (true/false):")
                                    recetaToUpdate.facil = readLine()!!.toBoolean()
                                }
                                "ingredientes" -> {
                                    println("Número de ingredientes:")
                                    val numIngredientes = readLine()!!.toInt()
                                    val ingredientes = mutableListOf<String>()
                                    for (i in 1..numIngredientes) {
                                        println("Ingrediente $i:")
                                        val ingrediente = readLine()!!
                                        ingredientes.add(ingrediente)
                                    }
                                    recetaToUpdate.ingredientes = ingredientes.toTypedArray()
                                }
                                "preparacion" -> {
                                    println("Nueva preparación:")
                                    recetaToUpdate.preparacion = readLine()!!
                                }
                                else -> {
                                    println("Atributo no válido.")
                                    return
                                }
                            }

                            Receta.actualizarReceta(recetaToUpdate)
                            println("Receta actualizada exitosamente.")
                        } else {
                            println("Receta con el ID $id no encontrada.")
                        }

                    }
                    4 -> {
                        println("Recetas existentes:")
                        val recetasExistentes = Receta.desplegarRecetas()
                        recetasExistentes.forEach { println(it) }

                        println("Ingrese el ID de la receta a borrar:")
                        val id = scanner.nextInt()
                        Receta.borrarReceta(id)
                        println("Receta borrada exitosamente.")
                    }
                    else -> {
                        println("Opcion invalida.")
                    }
                }
            }
            2 -> {
                println("Gestion de Cocineros:")
                println("1. Ingresar Cocinero")
                println("2. Desplegar Cocineros")
                println("3. Actulizar Cocinero")
                println("4. Borrar Cocinero")

                when (scanner.nextInt()) {
                    1 -> {
                        println("Enter the Cocinero details:")
                        println("Nombre:")
                        val nombre = readLine()
                        println("Edad:")
                        val edad = scanner.nextInt()
                        println("Puntuación de clientes:")
                        val puntuacionClientes = scanner.nextFloat()
                        println("Fecha de integracion (yyyy-MM-dd):")
                        val fechaCreacionString = readLine()
                        val formato = SimpleDateFormat("yyyy-MM-dd")
                        val fechaIntegracion = formato.parse(fechaCreacionString)

                        println("Autor de la receta (true/false):")
                        val autorReceta = scanner.nextBoolean()

                        // Prompt for Recetas
                        val recetas = mutableListOf<Receta>()
                        while (true) {
                            println("Do you want to add a Receta? (y/n)")
                            val choice = scanner.next()
                            if (choice.equals("y", ignoreCase = true)) {
                                val recetasExistentes = Receta.desplegarRecetas()
                                recetasExistentes.forEach { println(it) }
                                println("Ingrerse el Id de la receta para agregar:")
                                val recetaId = scanner.nextInt()
                                val recetaToAdd = Receta.desplegarRecetas().find { it.id == recetaId }
                                if (recetaToAdd != null) {
                                    recetas.add(recetaToAdd)
                                    println("Receta agregada exitosamente.")
                                } else {
                                    println("La receta con el el ID $recetaId no encontrada.")
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
                        println("Cocinero guardado.")

                    }
                    2 -> {
                    val cocineros = Cocinero.desplegarCocinero()
                    if (cocineros.isNotEmpty()) {
                        println("Cocineros:")
                        cocineros.forEach { println(it) }
                    } else {
                        println("Cocinero no encontrado.")
                    }
                    }
                    3 -> {
                        println("Cocineros existentes:")
                        val cocinerosExistentes = Cocinero.desplegarCocinero()
                        cocinerosExistentes.forEach { println(it) }

                        println("\nIngrese el ID del cocinero para actualizar:")
                        val id = readLine()!!.toInt()
                        val cocineroToUpdate = cocinerosExistentes.find { it.id == id }

                        if (cocineroToUpdate != null) {
                            println("Ingrese el atributo a actualizar (nombre, edad, costumersScore, fechaIntegracion, autor, recetas):")
                            val atributo = readLine()

                            when (atributo) {
                                "nombre" -> {
                                    println("Nuevo nombre:")
                                    cocineroToUpdate.nombre = readLine()!!
                                }
                                "edad" -> {
                                    println("Nueva edad:")
                                    cocineroToUpdate.edad = readLine()!!.toInt()
                                }
                                "costumersScore" -> {
                                    println("Nueva puntuación de clientes:")
                                    cocineroToUpdate.costumersScore = readLine()!!.toFloat()
                                }
                                "fechaIntegracion" -> {
                                    println("Fecha de integración (yyyy-MM-dd):")
                                    val integracionSF = readLine()!!
                                    val formato = SimpleDateFormat("yyyy-MM-dd")
                                    cocineroToUpdate.fechaIntegracion = formato.parse(integracionSF)
                                }
                                "autor" -> {
                                    println("Es el autor de la receta (true/false):")
                                    cocineroToUpdate.autor = readLine()!!.toBoolean()
                                }
                                "recetas" -> {
                                    println("Número de recetas:")
                                    val numRecetas = readLine()!!.toInt()
                                    val recetas = mutableListOf<Receta>()
                                    for (i in 1..numRecetas) {
                                        println("Receta $i:")
                                        val recetaId = readLine()!!.toInt()
                                        val receta = Receta.desplegarRecetas().find { it.id == recetaId }
                                        if (receta != null) {
                                            recetas.add(receta)
                                        }
                                    }
                                    cocineroToUpdate.recetas = recetas.toTypedArray()
                                }
                                else -> {
                                    println("Atributo no válido.")
                                    return
                                }
                            }

                            Cocinero.actualizarCocinero(cocineroToUpdate)
                            println("Cocinero actualizado.")
                        } else {
                            println("El cocinero con la ID $id indicada no existe.")
                        }

                    }
                    4 -> {
                        println("Cocineros existentes:")
                        val cocinerosExistentes = Cocinero.desplegarCocinero()
                        cocinerosExistentes.forEach { println(it) }

                        println("Ingrese el Id del cocinero a borrar:")
                        val id = scanner.nextInt()
                        Cocinero.borrarCocinero(id)
                        println("Cocinero borrado de manera exitosa")
                    }
                    else -> {
                        println("Opcion invalida.")
                    }
                }
            }
            0 -> {
                println("Saliendo...")
                return
            }
            else -> {
                println("Opcion invalida.")
            }
        }
    }
}



data class Receta(
    val id: Int,
    var nombre: String?,
    var porciones: Int,
    var calorias: Float,
    var creacion: Date,
    var facil: Boolean,
    var ingredientes: Array<String>,
    var preparacion: String
) {
    companion object {
        private const val archivo_recetas = "C:\\Users\\escritorio.virtual6\\Documents\\GitHub\\chicaiza-sandovalin-kharol-victoria-mov-com\\Tareas\\CRUD_Kotlin_Kharol_Chicaiza\\src\\main\\kotlin\\recetas.txt"

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

