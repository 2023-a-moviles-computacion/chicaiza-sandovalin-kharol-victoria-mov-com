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

class ingreso_cocineros : AppCompatActivity() {
    private val arreglo = BaseCocineros.arregloCocineros
    private var nextId = arreglo.size + 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso_cocineros)

        val botonRegresarCV = findViewById<ImageButton>(R.id.imbBackCV)
        botonRegresarCV
            .setOnClickListener {
                irActividad(Cocineros::class.java)
            }
        val botonAnadirC = findViewById<Button>(R.id.btnGuardarC)
        botonAnadirC
            .setOnClickListener{
                val nombre = findViewById<EditText>(R.id.etNombreC)
                val edad = findViewById<EditText>(R.id.etEdad)
                val score = findViewById<EditText>(R.id.etCostumerScore)
                val inputFecha = findViewById<EditText>(R.id.etFechaIntegracion)
                val fechaString = inputFecha.text.toString()

                // Create a SimpleDateFormat to parse the date input
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val fechaIntegracion: Date = try {
                    dateFormat.parse(fechaString)
                } catch (e: Exception) {
                    Date()
                }

                val check= findViewById<RadioButton>(R.id.rbtAutorSi)
                val autor = if(check.isChecked){
                    true
                }else{
                    false
                }
                val recetas = ArrayList<Receta>()


                val cocinero = Cocinero(nextId, nombre.text.toString(),edad.text.toString().toInt(),score.text.toString().toFloat(),fechaIntegracion,autor,recetas)
                BaseCocineros.arregloCocineros.add(cocinero)
                nextId  ++

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