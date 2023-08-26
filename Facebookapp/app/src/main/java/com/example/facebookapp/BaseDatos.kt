package com.example.facebookapp

import android.graphics.ColorSpace.Model

class BaseDatos {
    companion object{
        val arregloPosts = arrayListOf<ModeloPost>()

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
        }
    }
}