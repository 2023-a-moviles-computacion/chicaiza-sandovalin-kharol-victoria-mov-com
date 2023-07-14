package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class IngresoRecetas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso_recetas)

        val botonRegresarVr = findViewById<ImageButton>(R.id.imbRegresarV)
        botonRegresarVr.setOnClickListener {
            irActividad(Recetas::class.java)
        }


    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }
}