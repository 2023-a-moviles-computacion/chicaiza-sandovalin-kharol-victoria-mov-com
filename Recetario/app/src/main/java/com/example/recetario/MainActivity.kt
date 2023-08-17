package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BaseDatos.tablaCocinero = SQLHelperCocinero(applicationContext)
        BaseDatos.tablaReceta = SQLHelperRecetas(applicationContext)

        val botonChefs = findViewById<Button>(R.id.btnChefs)
        botonChefs
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