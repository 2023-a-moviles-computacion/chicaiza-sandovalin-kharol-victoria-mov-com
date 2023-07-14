package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class Recetas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recetas)


        // Ir a crear receta
        val botonCrearReceta = findViewById<Button>(R.id.btnAgregarC)
        botonCrearReceta
            .setOnClickListener {
                irActividad(IngresoRecetas::class.java)
            }

        //regresar al home
        val botonRegresarAHome = findViewById<ImageButton>(R.id.imbRegresarR)
        botonRegresarAHome
            .setOnClickListener {
                irActividad(MainActivity::class.java)
            }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }


}