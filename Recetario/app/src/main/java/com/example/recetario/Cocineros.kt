package com.example.recetario

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class Cocineros : AppCompatActivity() {
    private lateinit var adaptador: ArrayAdapter<Cocinero>
    val arregloCocineros= BaseCocineros.arregloCocineros
    var cocineroSeleccionado = 0

    val callbackContenidoCIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode== Activity.RESULT_OK){
                if(result.data !=null){
                    val data= result.data
                    val nombreModificado = data?.getStringExtra("nombreModificado")
                    val edadModificado = data?.getIntExtra("edadModificado", 0)
                    val scoreModificado = data?.getFloatExtra("scoreModificado", 0.0f)
                    val dateModificado = data?.getStringExtra("dateModificado")
                    val autorModificado = data?.getBooleanExtra("autorModificado",false)

                    // Update the corresponding Cocinero object in the arregloCocineros list
                    val cocinero = arregloCocineros[cocineroSeleccionado]
                    cocinero.nombre = nombreModificado
                    cocinero.edad = edadModificado

                    adaptador.notifyDataSetChanged()
                    adaptador.notifyDataSetChanged()
                }
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocineros)

        //Crea el list view y el adaptador
        val listView = findViewById<ListView>(R.id.lvCocineros)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BaseCocineros.arregloCocineros
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)


        val botonAgregarChef = findViewById<Button>(R.id.btnAgregarC)
        botonAgregarChef
            .setOnClickListener {
                irActividad(ingreso_cocineros::class.java)
            }

        val botonRegresarMC = findViewById<ImageButton>(R.id.btnBackC)
        botonRegresarMC
            .setOnClickListener {
                irActividad(MainActivity::class.java)
            }

        val botonRegresarHC = findViewById<ImageButton>(R.id.imbHomeC)
        botonRegresarHC
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
        inflater.inflate(R.menu.menu,menu)
        //obtener el id del Arraylist seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        cocineroSeleccionado=id
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.miEditarC ->{
                "${cocineroSeleccionado}"
                abrirActividadConParametros(editar_cocinero::class.java,cocineroSeleccionado)
                return true
            }
            R.id.miBorrarC ->{
                "${cocineroSeleccionado}"
                abrirDialogo(cocineroSeleccionado)
                return true
            }
            R.id.miRecetas ->{
                "${cocineroSeleccionado}"
                abrirActividadConParametros(Recetas::class.java,cocineroSeleccionado)
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
        intentExplito.putExtra("id",cocineroSeleccionado)

        callbackContenidoCIntentExplicito.launch(intentExplito)

    }



}