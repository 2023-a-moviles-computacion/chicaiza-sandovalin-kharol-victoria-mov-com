package com.example.recetario

import java.text.SimpleDateFormat
import java.util.*

class BaseCocineros {
    companion object {
        val arregloCocineros = arrayListOf<Cocinero>()

        init {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val fechaIntegracion = dateFormat.parse("2022-10-15")

            val recetas1 = arrayListOf<Receta>(
                Receta(1, "Ramen", 2, 300f, Date(), true, arrayOf("Fideo", "Carne de cerdo"), "Ponga agua caliente en el fideo y agregue la carne cocinada"),
                Receta(2, "Arroz con huevo", 4, 500f, Date(), false, arrayOf("Arroz", "huevo"), "Prepare el arroz y fria el huevo")
            )

            val recetas2 = arrayListOf<Receta>(
                Receta(3, "Sopa de hueso", 3, 400f, Date(), true, arrayOf("Hueso", "Verduras"), "Suavice el hueso por una hora y luego a;ada las verduras"),
                Receta(4, "pescado al vapor", 2, 200f, Date(), false, arrayOf("Pescado", "Especias"), "Sazone el pescado con las especias y luego pongalo en una olla para cocinar al vapor cubriendolo con papel aluminio")
            )

            val recetas3 = arrayListOf<Receta>(
                Receta(3, "Carne estofada", 3, 400f, Date(), true, arrayOf("Carne de res", "Zanahoria","Tomate"), "Licue las verduras con un poco de agua y cocine la carne con el preparado"),
                Receta(4, "Sanduche de pollo", 2, 200f, Date(), false, arrayOf("Pan", "Pollo","Lechuga"), "Corte el pan en dos, agregue el pollo cocinado y la lechuga encima")
            )

            arregloCocineros.add(
                Cocinero(1, "Grodon Ramsay", 25, 4.5f, fechaIntegracion, true, recetas1)
            )
            arregloCocineros.add(
                Cocinero(2, "Carolina Herrera", 30, 3.8f, fechaIntegracion, false, recetas2)
            )
            arregloCocineros.add(
                Cocinero(3, "Parmenidez Castro", 28, 4.2f, Date(), true, recetas3)
            )
        }
    }
}
