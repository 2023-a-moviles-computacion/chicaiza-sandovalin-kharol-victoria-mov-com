package com.example.recetario

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
                val idCocinero = cocineroSeleccionado +1
                val foranea= idCocinero .toString()
                val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
                val porciones = findViewById<EditText>(R.id.etPorciones).text.toString()
                val calorias = findViewById<EditText>(R.id.etCalorias).text.toString()
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
                    "true"
                }else{
                    "false"
                }
                val ingredientes = findViewById<EditText>(R.id.etIngredientes).text.toString()

                val preparacion = findViewById<EditText>(R.id.etPreparacion).text.toString()

                /*BaseDatos.tablaReceta!!.crearReceta(
                    foranea,nombre.text.toString(),porciones.text.toString().toInt(),calorias.text.toString().toFloat(),fechaString,facil,ingredientes.text.toString(),preparacion.text.toString()
                )*/
                val db = Firebase.firestore
                val recetas = db.collection("recetas")
                val data = hashMapOf(
                    "calorias" to calorias,
                    "creacion" to fechaString,
                    "facil" to facil,
                    "ingredientes" to ingredientes,
                    "nombre" to nombre,
                    "porciones" to porciones,
                    "preparacion" to preparacion,
                    "foranea" to foranea
                )
                consultarReceta(cocineroSeleccionado) { conteo ->
                    recetas.document("${conteo + 1}").set(data)
                    val intent = Intent(this, Recetas::class.java)
                    intent.putExtra("id",idCocinero)
                    startActivity(intent)

                }




            }


    }



    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }

    fun consultarReceta(cocineroSeleccionado: Int, callback: (Int) -> Unit) {
        var conteo = 0
        val db = Firebase.firestore
        val recetasRefUnico = db.collection("recetas")

        recetasRefUnico
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val chef = (document.data?.get("foranea") as String?)?.toInt()
                    //if (cocineroSeleccionado == chef) {
                        conteo++
                    //}
                }
                callback(conteo) // Notify the callback with the conteo value
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur during the operation
                println("Error getting documents: $exception")
                callback(-1) // You can use a negative value to indicate an error if needed
            }
    }

}