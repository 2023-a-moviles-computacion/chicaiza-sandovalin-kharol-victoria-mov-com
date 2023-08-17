package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import java.text.SimpleDateFormat
import java.util.Date

class IngresoRecetas : AppCompatActivity() {

    var arregloRecetas: ArrayList<Receta>? = null
    var cocineroSeleccionado=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso_recetas)
        //recibir el parametro
        cocineroSeleccionado = intent.getIntExtra("id", 1)
        val cocinero = BaseCocineros.arregloCocineros.find { it.id == cocineroSeleccionado+1 }



        val botonRegresarVr = findViewById<ImageButton>(R.id.imbRegresarV)
        botonRegresarVr.setOnClickListener {
            irActividad(Recetas::class.java)
        }

        val botonAnadirR = findViewById<Button>(R.id.btnGuardarR)
        botonAnadirR
            .setOnClickListener{
                val foranea= cocineroSeleccionado
                val nombre = findViewById<EditText>(R.id.etNombre)
                val porciones = findViewById<EditText>(R.id.etPorciones)
                val calorias = findViewById<EditText>(R.id.etCalorias)
                val inputFecha = findViewById<EditText>(R.id.etCreacion)
                val fechaString = inputFecha.text.toString()

                // Create a SimpleDateFormat to parse the date input
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val fechaIntegracion: Date = try {
                    dateFormat.parse(fechaString)
                } catch (e: Exception) {
                    Date()
                }

                val check= findViewById<RadioButton>(R.id.rbtSi)
                val facil = if(check.isChecked){
                    true
                }else{
                    false
                }
                val ingredientes = findViewById<EditText>(R.id.etIngredientes)

                val preparacion = findViewById<EditText>(R.id.etPreparacion)

                BaseDatos.tablaReceta!!.crearReceta(
                    foranea,nombre.text.toString(),porciones.text.toString().toInt(),calorias.text.toString().toFloat(),fechaString,facil,ingredientes.text.toString(),preparacion.text.toString()
                )

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