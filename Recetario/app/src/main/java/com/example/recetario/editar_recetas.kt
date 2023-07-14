package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class editar_recetas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_recetas)

        val botonRegresarERH = findViewById<ImageButton>(R.id.imbERegresarRH)
        botonRegresarERH
            .setOnClickListener {
                irActividad(Cocineros::class.java)
            }
    }


    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }
}