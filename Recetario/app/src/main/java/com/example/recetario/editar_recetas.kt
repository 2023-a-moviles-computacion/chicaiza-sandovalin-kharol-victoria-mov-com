package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import java.text.SimpleDateFormat

class editar_recetas : AppCompatActivity() {
    var arregloRecetas: ArrayList<Receta>? = null
    var cocineroSeleccionado=0
    var recetaSeleccionado=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_recetas)
        //recibir el parametro
        cocineroSeleccionado = intent.getIntExtra("cocinero", 0)
        recetaSeleccionado = intent.getIntExtra("receta", 0)
        val cocinero = BaseCocineros.arregloCocineros.find { it.id == cocineroSeleccionado+1 }
        arregloRecetas = cocinero?.recetas ?: arrayListOf()
        var nextId = arregloRecetas?.size?.plus(1) ?: 1
        val receta=arregloRecetas?.get(recetaSeleccionado)

        //nombre
        val nombreText = findViewById<EditText>(R.id.etENombreR)
        val nombreInicial = receta?.nombre.toString()
        nombreText.setText(nombreInicial)

        //porciones
        val porcionesText = findViewById<EditText>(R.id.edEPorciones)
        val porcionesInicial = receta?.porciones.toString()
        porcionesText.setText(porcionesInicial)


        //calorias
        val calText = findViewById<EditText>(R.id.etECalorias)
        val calInicial = receta?.calorias.toString()
        calText.setText(calInicial)


        //fecha integracion
        val fechaText = findViewById<EditText>(R.id.etEFechaCreacion)
        val formato = SimpleDateFormat("yy-MM-dd")
        val fechaInicial = formato.format(receta?.creacion)
        fechaText.setText(fechaInicial)



        //facil
        val facilText = findViewById<EditText>(R.id.etEFacil)
        val facilInicial = receta?.facil.toString()
        facilText.setText(facilInicial)

        //Ingredientes
        val ingreText = findViewById<EditText>(R.id.etEIngredientes)
        val ingreInicial = receta?.ingredientes?.joinToString(", ")
        ingreText.setText(ingreInicial)



        //preparacion
        val prepaText = findViewById<EditText>(R.id.edEPreparacion)
        val prepaInicial = receta?.preparacion.toString()
        prepaText.setText(prepaInicial)


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

                val formato = SimpleDateFormat("yyyy-MM-dd")
                val fechaF = formato.parse(fecha)

                val intentDevolverParametros = Intent()
                intentDevolverParametros.putExtra("nombreModificado",nombre)
                intentDevolverParametros.putExtra("porcionesModificado",porciones.toInt())
                intentDevolverParametros.putExtra("caloriasModificado",calorias.toFloat())
                intentDevolverParametros.putExtra("dateModificado",fecha)
                intentDevolverParametros.putExtra("facilModificado",facil.toBoolean())
                intentDevolverParametros.putExtra("ingredientesModificado",ingredientes)
                intentDevolverParametros.putExtra("preparacionModificado",preparacion)

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
}