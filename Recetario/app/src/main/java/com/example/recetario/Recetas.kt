package com.example.recetario

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class Recetas : AppCompatActivity() {

    var arregloRecetas: ArrayList<Receta>? = null
    private lateinit var adaptador: ArrayAdapter<Receta>

    var recetaSeleccionada = 0
    var cocineroSeleccionado=0
    var recetasList = BaseDatos.tablaReceta?.consultarRecetasPorForanea(cocineroSeleccionado) ?: emptyList()


    val callbackContenidoCIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode== Activity.RESULT_OK){
                if(result.data !=null){
                    val data= result.data
                    val foraneaModificado = data?.getIntExtra("foraneaModificado", 0)
                    val nombreModificado = data?.getStringExtra("nombreModificado")
                    val porcionesModificado = data?.getIntExtra("porcionesModificado", 0)
                    val caloriasModificado = data?.getFloatExtra("caloriasModificado", 0.0f)
                    val dateModificado = data?.getStringExtra("dateModificado")
                    val facilModificado = data?.getBooleanExtra("facilModificado",false)
                    val formato = SimpleDateFormat("yyyy-MM-dd")
                    val fechaF = formato.parse(dateModificado)
                    val ingredientesModificado = data?.getStringExtra("ingredientesModificado")
                    val preparacionModificado = data?.getStringExtra("preparacionModificado")

                    val ingredientes = ingredientesModificado
                    var receta = Receta(
                        recetaSeleccionada,
                        foraneaModificado,
                    nombreModificado,
                    porcionesModificado,
                    caloriasModificado,
                        dateModificado,
                    facilModificado,
                        ingredientes,
                    preparacionModificado)
                    BaseDatos.tablaReceta!!.actualizarRecetaFormulario(

                        cocineroSeleccionado,
                        nombreModificado.toString(),
                        porcionesModificado.toString().toInt(),
                        caloriasModificado.toString().toFloat(),
                        dateModificado.toString(),
                        facilModificado.toString().toBoolean(),
                        ingredientes.toString(),
                        preparacionModificado.toString(),
                        recetaSeleccionada
                    )


                    //adaptador.notifyDataSetChanged()
                    var id=cocineroSeleccionado
                    abrirActividadConParametro(Recetas::class.java,cocineroSeleccionado)


                }
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recetas)
        //recibir el parametro
         cocineroSeleccionado = intent.getIntExtra("id", 1)
        //val cocinero = BaseCocineros.arregloCocineros.find { it.id == cocineroSeleccionado+1 }


        //Crea el list view y el adaptador
        val listView = findViewById<ListView>(R.id.lvRecetas)
// Call the function to retrieve Receta objects
         recetasList = BaseDatos.tablaReceta?.consultarRecetasPorForanea(cocineroSeleccionado) ?: emptyList()
        println("si llega a esta parte")
// Create the adapter
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            recetasList.toList()
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)


        // Ir a crear receta
        val botonCrearReceta = findViewById<Button>(R.id.btnAgregarC)
        botonCrearReceta
            .setOnClickListener {
                irActividad(IngresoRecetas::class.java)
            }
        //regresar al home
        val botonRegresarAC = findViewById<ImageButton>(R.id.imbRegresarRC)
        botonRegresarAC
            .setOnClickListener {
                devolverRespuesta()
                irActividad(Cocineros::class.java)
            }
        //regresar al home
        val botonRegresarAHome = findViewById<ImageButton>(R.id.ibRegresarHR)
        botonRegresarAHome
            .setOnClickListener {
                irActividad(MainActivity::class.java)
            }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent= Intent(this,clase)
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //Llenamos las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menur,menu)
        //obtener el id del Arraylist seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        recetaSeleccionada=id+1
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_receta ->{
                "${recetaSeleccionada}"
                abrirActividadConParametros(editar_recetas::class.java,recetaSeleccionada,cocineroSeleccionado)
                return true
            }
            R.id.mi_eliminar_receta ->{
                "${recetaSeleccionada}"
                abrirDialogo(cocineroSeleccionado,recetaSeleccionada)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }

    }

    fun abrirDialogo(cocineroIndex: Int,recetaIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val id = recetaIndex
            BaseDatos.tablaReceta!!.eliminarRecetaFormulario(
                id.toString().toInt()
            )
            //adaptador.notifyDataSetChanged()
            dialog.dismiss()
            abrirActividadConParametro(Recetas::class.java,cocineroSeleccionado)

        }
        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()
    }

    fun abrirActividadConParametros(
        clase: Class<*>, recetaSeleccionado:Int,cocineroSeleccionado:Int
    ){
        val intentExplito= Intent(this,clase)
        intentExplito.putExtra("cocinero",cocineroSeleccionado)
        intentExplito.putExtra("receta",recetaSeleccionado)

        callbackContenidoCIntentExplicito.launch(intentExplito)

    }
    fun abrirActividadConParametro(
        clase: Class<*>, cocineroSeleccionado:Int
    ){
        val intentExplito= Intent(this,clase)
        intentExplito.putExtra("id",cocineroSeleccionado)

        callbackContenidoCIntentExplicito.launch(intentExplito)

    }

    fun devolverRespuesta(){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreModificado","Pamela")
        intentDevolverParametros.putExtra("edadModificado",33)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }


}