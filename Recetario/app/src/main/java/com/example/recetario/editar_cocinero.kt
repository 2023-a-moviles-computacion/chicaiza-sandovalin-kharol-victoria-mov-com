package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import java.text.SimpleDateFormat

class editar_cocinero : AppCompatActivity() {

    val arregloCocineros= BaseCocineros.arregloCocineros

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cocinero)

        //recibir parametros
        val id = intent.getIntExtra("id",0)

        val cocinero=arregloCocineros.get(id)

        //nombre
        val nombreText = findViewById<EditText>(R.id.etENombre)
        val nombreInicial = cocinero.nombre.toString()
        nombreText.setText(nombreInicial)
        val nombre = nombreText.text.toString()

        //edad
        val edadText = findViewById<EditText>(R.id.edEEdad)
        val edadInicial = cocinero.edad.toString()
        edadText.setText(edadInicial)
        val edad = edadText.text.toString()

        //costumerScore
        val scoreText = findViewById<EditText>(R.id.etEPuntuacion)
        val scoreInicial = cocinero.costumersScore.toString()
        scoreText.setText(scoreInicial)
        val score = scoreText.text.toString()

        //fecha integracion
        val fechaText = findViewById<EditText>(R.id.etEFecha)
        val fechaInicial = cocinero.fechaIntegracion.toString()
        fechaText.setText(fechaInicial)
        val fecha = fechaText.text.toString()

        //autor
        val autorText = findViewById<EditText>(R.id.etEAutor)
        val autorInicial = cocinero.autor.toString()
        autorText.setText(autorInicial)
        val autor = autorText.text.toString()

        val botonRegresarECH = findViewById<ImageButton>(R.id.imbERegresar)
        botonRegresarECH
            .setOnClickListener {
                irActividad(Cocineros::class.java)
            }
        val botonEditarC = findViewById<Button>(R.id.btnEditar)
        botonEditarC
            .setOnClickListener {
                val nuevococinero = BaseCocineros.arregloCocineros[id]
                nuevococinero.nombre=nombre
                nuevococinero.edad = edad.toInt()
                nuevococinero.costumersScore = score.toFloat()
                val formato = SimpleDateFormat("yyyy-MM-dd")
                val fechaF = formato.parse(fecha)
                nuevococinero.fechaIntegracion =fechaF
                nuevococinero.autor = autor.toBoolean()
                BaseCocineros.arregloCocineros[id] = nuevococinero
                val intentDevolverParametros = Intent()
                intentDevolverParametros.putExtra("nombreModificado",nombre)
                intentDevolverParametros.putExtra("edadModificado",edad.toInt())
                intentDevolverParametros.putExtra("scoreModificado",score.toFloat())
                intentDevolverParametros.putExtra("dateModificado",fecha)
                intentDevolverParametros.putExtra("autorModificado",autor.toBoolean())

                setResult(
                    RESULT_OK,
                    intentDevolverParametros
                )
                finish()
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