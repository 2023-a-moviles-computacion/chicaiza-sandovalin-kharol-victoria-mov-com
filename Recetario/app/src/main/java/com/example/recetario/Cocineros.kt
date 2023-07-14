package com.example.recetario

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
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class Cocineros : AppCompatActivity() {

    val arregloCocineros= BaseCocineros.arregloCocineros
    var cocineroSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocineros)

        //Crea el list view y el adaptador
        val listView = findViewById<ListView>(R.id.lvCocineros)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloCocineros
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()


        val botonAgregarChef = findViewById<Button>(R.id.btnAgregarC)
        botonAgregarChef
            .setOnClickListener {
                irActividad(Nuevo_cocinero::class.java)
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
                return true
            }
            R.id.miBorrarC ->{
                "${cocineroSeleccionado}"
                abrirDialogo()
                return true
            }
            else -> return super.onContextItemSelected(item)
        }

    }


    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which->

            }
        )
        builder.setNegativeButton(
            "cancelar",
            null
        )

    }


}