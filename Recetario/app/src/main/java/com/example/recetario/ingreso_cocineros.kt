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
                val nombre1 = findViewById<EditText>(R.id.etNombreC)
                val edad1 = findViewById<EditText>(R.id.etEdad)
                val score1 = findViewById<EditText>(R.id.etCostumerScore)
                val inputFecha1 = findViewById<EditText>(R.id.etFechaIntegracion)

                val nombre =  nombre1.text.toString()
                val edad = edad1.text.toString().toInt()
                val costumersScore = score1.text.toString().toFloat()
                val fechaIntegracion= inputFecha1.text.toString()


                // Create a SimpleDateFormat to parse the date input
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")

                val check= findViewById<RadioButton>(R.id.rbtAutorSi)
                var autor=false
                 autor = if(check.isChecked){
                    true
                }else{
                    false
                }


               // val cocinero = Cocinero(nextId, nombre.text.toString(),edad.text.toString().toInt(),score.text.toString().toFloat(),fechaString,autor)
               // BaseCocineros.arregloCocineros.add(cocinero)
                BaseDatos.tablaReceta!!.crearReceta(1,"Arroz", 3,300.2f,"12-05-2023",true,"arroz, ajo, agua","llenar una olla de agua y cuando hierva agragar el ajo y la sal, dejar reposar por cicno minutos y agregar el arroz a llama baja")

                BaseDatos.tablaCocinero!!.crearCocinero(
                    nombre,edad,costumersScore,fechaIntegracion,autor
                )

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