package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ingreso_cocineros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso_cocineros)

        val botonRegresarCV = findViewById<ImageButton>(R.id.imbBackCV)
        botonRegresarCV
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