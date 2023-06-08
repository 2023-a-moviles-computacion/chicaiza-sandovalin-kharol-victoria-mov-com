package com.example.movilescomp2023a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// en AppCompatActivity va a estar toda la logica que vemos aqui
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irActividad(
    clase: Class<*>
    ){
        val intent=Intent(this,clase)
        startActivity(intent)
    }
}