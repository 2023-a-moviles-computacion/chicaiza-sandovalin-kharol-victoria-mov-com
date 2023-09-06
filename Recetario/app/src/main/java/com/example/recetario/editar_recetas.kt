package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class editar_recetas : AppCompatActivity() {
    var arregloRecetas: ArrayList<Receta>? = null
    var cocineroSeleccionado=0
    var recetaSeleccionado=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_recetas)

        //tomar los edit text
        val nombreText = findViewById<EditText>(R.id.etENombreR)
        val porcionesText = findViewById<EditText>(R.id.edEPorciones)
        val calText = findViewById<EditText>(R.id.etECalorias)
        val fechaText = findViewById<EditText>(R.id.etEFechaCreacion)
        val facilText = findViewById<EditText>(R.id.etEFacil)
        val ingreText = findViewById<EditText>(R.id.etEIngredientes)
        val prepaText = findViewById<EditText>(R.id.edEPreparacion)


        //recibir el parametro
        cocineroSeleccionado = intent.getIntExtra("cocinero", 1)
        recetaSeleccionado = intent.getIntExtra("receta", 1)
        val cocinero = BaseCocineros.arregloCocineros.find { it.id == cocineroSeleccionado+1 }

        var nextId = arregloRecetas?.size?.plus(1) ?: 1
       // val receta=arregloRecetas?.get(recetaSeleccionado)
        //val receta=BaseDatos.tablaReceta!!.consultarRecetaPorID(recetaSeleccionado)
        consultarReceta(recetaSeleccionado) { receta ->
            println("este es el cocinero"+cocineroSeleccionado)
            println("esta es la receta"+recetaSeleccionado)
            //nombre

            val nombreInicial = receta?.nombre.toString()
            nombreText.setText(nombreInicial)

            //porciones
            val porcionesInicial = receta?.porciones.toString()
            porcionesText.setText(porcionesInicial)


            //calorias
            val calInicial = receta?.calorias.toString()
            calText.setText(calInicial)


            //fecha integracion
            val fechaInicial = receta?.creacion.toString()
            fechaText.setText(fechaInicial)


            //facil
            val facilInicial = receta?.facil.toString()
            facilText.setText(facilInicial)

            //Ingredientes
            val ingreInicial = receta?.ingredientes.toString()
            ingreText.setText(ingreInicial)


            //preparacion
            val prepaInicial = receta?.preparacion.toString()
            prepaText.setText(prepaInicial)
        }

        val botonRegresarERH = findViewById<ImageButton>(R.id.imbERegresarRH)
        botonRegresarERH
            .setOnClickListener {
                irActividad(Cocineros::class.java)
            }

        val botonEditarR = findViewById<Button>(R.id.btnEditarR)
        botonEditarR
            .setOnClickListener{
                val nombre = nombreText.text.toString()
                val porciones = porcionesText.text.toString()
                val calorias = calText.text.toString()
                val fecha = fechaText.text.toString()
                val facil = facilText.text.toString()
                val ingredientes = ingreText.text.toString()
                val preparacion=prepaText.text.toString()
                val foranea = cocineroSeleccionado.toString()

              //  val formato = SimpleDateFormat("yyyy-MM-dd")
                //val fechaF = formato.parse(fecha)

                val intentDevolverParametros = Intent()
                intentDevolverParametros.putExtra("nombreModificado",nombre)
               /* intentDevolverParametros.putExtra("porcionesModificado",porciones.toInt())
                intentDevolverParametros.putExtra("caloriasModificado",calorias.toFloat())
                intentDevolverParametros.putExtra("dateModificado",fecha)
                intentDevolverParametros.putExtra("facilModificado",facil.toBoolean())
                intentDevolverParametros.putExtra("ingredientesModificado",ingredientes)
                intentDevolverParametros.putExtra("preparacionModificado",preparacion)*/
                val db =Firebase.firestore
                val recetas = db.collection("recetas")
                val data = hashMapOf(
                    "calorias" to calorias,
                    "creacion" to fecha,
                    "facil" to facil,
                    "ingredientes" to ingredientes,
                    "nombre" to nombre,
                    "porciones" to porciones,
                    "preparacion" to preparacion,
                    "foranea" to foranea
                )
                recetas.document("${recetaSeleccionado}").set(data)
                setResult(
                    RESULT_OK,
                    intentDevolverParametros
                )
                finish()
            }
    }


    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }
    fun consultarReceta(valor: Int, callback: (Receta) -> Unit) {
        val db = Firebase.firestore
        val recetaRefUnico = db.collection("recetas")
        var rcetaEncontrado = Receta(0,0,"",0,0.0f,"",false,"","")
        recetaRefUnico
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val igual = document.id.toInt()
                    println(igual)
                    println(valor)
                    if (igual == valor) {
                        println("si entra")
                        rcetaEncontrado = Receta(
                            document.id.toInt(),
                            (document.data?.get("foranea") as String?)?.toInt(),
                            document.data?.get("nombre") as String?,
                            (document.data?.get("porciones") as String?)?.toInt(),
                            (document.data?.get("calorias") as String?)?.toFloat(),
                            document.data?.get("creacion") as String?,
                            (document.data?.get("facil") as String?).toBoolean(),
                            document.data?.get("ingredientes") as String?,
                            document.data?.get("preparacion") as String?,
                        )
                        // Invoke the callback with the cocineroEncontrado object
                        callback(rcetaEncontrado)
                        return@addOnSuccessListener
                    }
                }
                // If not found, invoke the callback with a default Cocinero object
                callback(rcetaEncontrado)
            }
            .addOnFailureListener { }

        // No need to return anything here
    }
    fun limpiarArreglo(){
        BaseDatos.recetasFire.clear()
    }


}