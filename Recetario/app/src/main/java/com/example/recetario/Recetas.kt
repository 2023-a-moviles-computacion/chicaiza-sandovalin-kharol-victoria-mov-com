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

class Recetas : AppCompatActivity() {

    val callbackContenidoCIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode== Activity.RESULT_OK){
                if(result.data !=null){
                    val data= result.data
                    "${data?.getStringExtra("recetaModificado")}"
                }
            }
        }

    var arregloRecetas: ArrayList<Receta>? = null
    private lateinit var adaptador: ArrayAdapter<Receta>
    var recetaSeleccionada = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recetas)
        //recibir el parametro
        val cocineroSeleccionado = intent.getIntExtra("id", 0)
        val cocinero = BaseCocineros.arregloCocineros.find { it.id == cocineroSeleccionado }
        arregloRecetas = cocinero?.recetas ?: arrayListOf()
        arregloRecetas = cocinero?.recetas ?: arrayListOf()
        Log.d("Recetas", "Recetas: $arregloRecetas")

        //Crea el list view y el adaptador
        val listView = findViewById<ListView>(R.id.lvRecetas)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloRecetas!!
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
        recetaSeleccionada=id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editar_receta ->{
                "${recetaSeleccionada}"
                abrirActividadConParametros(editar_recetas::class.java,recetaSeleccionada)
                return true
            }
            R.id.mi_eliminar_receta ->{
                "${recetaSeleccionada}"
                abrirDialogo(recetaSeleccionada)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }

    }

    fun abrirDialogo(cocineroIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            BaseCocineros.arregloCocineros.removeAt(cocineroIndex)
            adaptador.notifyDataSetChanged()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()
        dialog.show()
    }

    fun abrirActividadConParametros(
        clase: Class<*>, cocineroSeleccionado:Int
    ){
        val intentExplito= Intent(this,clase)
        intentExplito.putExtra("cocinero",cocineroSeleccionado)

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