package com.example.facebookapp

import android.graphics.ColorSpace.Model
import java.text.Normalizer

class BaseDatos {
    companion object{
        val arregloPosts = arrayListOf<ModeloPost>()
        val arregloNotificaciones= arrayListOf<Notificacion>()

        init {
            arregloPosts
                .add(
                    ModeloPost(1,5,3,R.drawable.perfil12,R.drawable.pintura,"Carlos Mendoza","2 hrs","He experimentado con muchos colores ultimamente, espero que les guste")
                )
            arregloPosts
                .add(
                    ModeloPost(2,26,6,R.drawable.perfil13,R.drawable.pasteles,"Isabel Allende", "8 hrs","Venta de pasteles en 3 días, vayan pidiendo el suyo con anticipación!!!")
                )
            arregloPosts
                .add(
                    ModeloPost(3,17,5,R.drawable.perfil14,R.drawable.messi,"Edison Gutierres", "2 mins","Ganando como siempre, grande Messi")
                )
            arregloNotificaciones
                .add(
                    Notificacion(1,R.drawable.perfil14,"Pedro Perez ha dado like a tu última publicación", "3 hrs")
                )
            arregloNotificaciones
                .add(
                    Notificacion(2,R.drawable.perfil13,"Carolina Cevallos ha compartido un post de meme Pepito","16hrs")
                )
            arregloNotificaciones
                .add(
                    Notificacion(3,R.drawable.perfil12,"Maria Castro ha modificado su foto de perfil", "24 hrs")
                )
        }
    }
}