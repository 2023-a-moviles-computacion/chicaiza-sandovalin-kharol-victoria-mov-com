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
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class Cocineros : AppCompatActivity() {
    private lateinit var adaptador: ArrayAdapter<Cocinero>

    var cocineroSeleccionado = 0
    var cocineroList = BaseDatos.tablaCocinero?.retrieveAllCocineros() ?: emptyList()
    var list=cocineroList.toList()

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
                    val formato = SimpleDateFormat("yyyy-MM-dd")
                    val fechaF = formato.parse(dateModificado)

                    val nombre =  nombreModificado.toString()
                    val edad = edadModificado
                    val costumersScore = scoreModificado
                    val fechaIntegracion= dateModificado.toString()
                    var autor= autorModificado.toString().toBoolean()


                    // Update the corresponding Cocinero object in the arregloCocineros list
                    val id= cocineroSeleccionado

                    BaseDatos.tablaCocinero!!.actualizarCocineroFormulario(

                        nombre,
                        edad,
                        costumersScore,
                        fechaIntegracion,
                        autor,
                        id
                    )

                    cocineroList = BaseDatos.tablaCocinero?.retrieveAllCocineros() ?: emptyList()
                    for (cocinero in cocineroList) {
                        println("ID: ${cocinero.id}")
                        println("Nombre: ${cocinero.nombre}")
                        println("Edad: ${cocinero.edad}")
                        // Print other properties
                        println("-----------------------")
                    }
                    list=cocineroList.toList()
                    adaptador.notifyDataSetChanged()
                    irActividad(Cocineros::class.java)

                }
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocineros)


// Create the list view and the adapter
        val listView = findViewById<ListView>(R.id.lvCocineros)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            list// Convert ArrayList to List
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

        cocineroSeleccionado=id+1
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
            val id = cocineroIndex
            BaseDatos.tablaCocinero!!.eliminarCocineroFormulario(
                id
            )
//            BaseCocineros.arregloCocineros.removeAt(cocineroIndex)
            adaptador.notifyDataSetChanged()
            dialog.dismiss()
            irActividad(Cocineros::class.java)

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