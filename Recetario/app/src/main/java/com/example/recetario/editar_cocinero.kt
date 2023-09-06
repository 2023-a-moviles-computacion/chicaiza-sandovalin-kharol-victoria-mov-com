package com.example.recetario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class editar_cocinero : AppCompatActivity() {

    val arregloCocineros= BaseCocineros.arregloCocineros

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cocinero)
        val nombreText = findViewById<EditText>(R.id.etENombre)
        val edadText = findViewById<EditText>(R.id.edEEdad)
        val scoreText = findViewById<EditText>(R.id.etEPuntuacion)
        val fechaText = findViewById<EditText>(R.id.etEFecha)
        val autorText = findViewById<EditText>(R.id.etEAutor)

        //recibir parametros
        val id = intent.getIntExtra("id",0)

       // val cocinero=BaseDatos.tablaCocinero!!.consultarCocineroPorID(id)
        consultarCocinero(id) { cocinero ->
            // Use cocinero object here
            println("Cocinero encontrado: $cocinero")
            //nombre

            val nombreInicial = cocinero.nombre.toString()
            nombreText.setText(nombreInicial)


            //edad

            val edadInicial = cocinero.edad.toString()
            edadText.setText(edadInicial)


            //costumerScore

            val scoreInicial = cocinero.costumersScore.toString()
            scoreText.setText(scoreInicial)


            //fecha integracion

            val fechaInicial = cocinero.fechaIntegracion.toString()
            fechaText.setText(fechaInicial)



            //autor

            val autorInicial = cocinero.autor.toString()
            autorText.setText(autorInicial)

        }



        val botonRegresarECH = findViewById<ImageButton>(R.id.imbERegresar)
        botonRegresarECH
            .setOnClickListener {
                irActividad(Cocineros::class.java)
            }
        val botonEditarC = findViewById<Button>(R.id.btnEditar)
        botonEditarC
            .setOnClickListener {
               // consultarCocineros()
                val nombre = nombreText.text.toString()
                val edad = edadText.text.toString()
                val score = scoreText.text.toString()
                val fecha = fechaText.text.toString()
                val autor = autorText.text.toString()

                val db =Firebase.firestore
                val cocineros = db.collection("cocineros")
                val data = hashMapOf(
                    "autor" to autor,
                    "costumersScore" to score,
                    "edad" to edad,
                    "nombre" to nombre,
                    "fechaIntegracion" to fecha
                )
                cocineros.document("${id}").set(data)
                //consultarCocineros()

                val intentDevolverParametros = Intent()
                intentDevolverParametros.putExtra("nombreModificado",nombre)


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


    /*fun consultarCocinero(
        valor:Int
    ):Cocinero{
        val db= Firebase.firestore
        val cocinerosRefUnico =db.collection("cocineros")
        var cocineroEncontrado = Cocinero(0,"0",0,0f,"0",false)

        cocinerosRefUnico
            .get()
            .addOnSuccessListener { querySnapshot->
                for (document in querySnapshot) {
                    var igual=document.id.toInt()
                    println(igual)
                    println(valor)
                    if (igual == valor){
                        println("si entra")
                        cocineroEncontrado =Cocinero(
                            document.id.toInt(),
                            document.data?.get("nombre") as String?,
                            (document.data?.get("edad") as String?)?.toInt(),
                            (document.data?.get("costumersScore") as String?)?.toFloat(),
                            document.data?.get("fechaIntegracion") as String?,
                            (document.data?.get("autor") as String?).toBoolean()
                        )
                    }

                }

            }
            .addOnFailureListener {  }

        return cocineroEncontrado
    }*/
    fun consultarCocinero(valor: Int, callback: (Cocinero) -> Unit) {
        val db = Firebase.firestore
        val cocinerosRefUnico = db.collection("cocineros")
        var cocineroEncontrado = Cocinero(0, "0", 0, 0f, "0", false)

        cocinerosRefUnico
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val igual = document.id.toInt()
                    println(igual)
                    println(valor)
                    if (igual == valor) {
                        println("si entra")
                        cocineroEncontrado = Cocinero(
                            document.id.toInt(),
                            document.data?.get("nombre") as String?,
                            (document.data?.get("edad") as String?)?.toInt(),
                            (document.data?.get("costumersScore") as String?)?.toFloat(),
                            document.data?.get("fechaIntegracion") as String?,
                            (document.data?.get("autor") as String?).toBoolean()
                        )
                        // Invoke the callback with the cocineroEncontrado object
                        callback(cocineroEncontrado)
                        return@addOnSuccessListener
                    }
                }
                // If not found, invoke the callback with a default Cocinero object
                callback(cocineroEncontrado)
            }
            .addOnFailureListener { }

        // No need to return anything here
    }
    fun limpiarArreglo(){
        BaseDatos.cocinerosFire.clear()
    }
    fun consultarCocineros(){
        val db=Firebase.firestore
        val cocinerosRefUnico =db.collection("cocineros")
        limpiarArreglo()
        cocinerosRefUnico
            .get()
            .addOnSuccessListener { querySnapshot->
                for (document in querySnapshot) {
                    BaseDatos.cocinerosFire
                        .add(
                            Cocinero(
                                document.id.toInt(),
                                document.data?.get("nombre") as String?,
                                (document.data?.get("edad") as String?)?.toInt(),
                                (document.data?.get("costumersScore") as String?)?.toFloat(),
                                document.data?.get("fechaIntegracion") as String?,
                                (document.data?.get("autor") as String?).toBoolean()
                            )
                        )
                }
            }
            .addOnFailureListener {  }

    }


}