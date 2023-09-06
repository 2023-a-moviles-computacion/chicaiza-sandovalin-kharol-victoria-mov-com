package com.example.recetario

import android.app.Activity
import android.app.DownloadManager
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
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class Cocineros : AppCompatActivity() {
    private lateinit var adaptador: ArrayAdapter<Cocinero>

    var cocineroSeleccionado = 0
    var cocineroList = BaseDatos.tablaCocinero?.retrieveAllCocineros() ?: emptyList()
    var query : Query? = null
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
                  /*  val edadModificado = data?.getIntExtra("edadModificado", 0)
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
*/

                    // Update the corresponding Cocinero object in the arregloCocineros list
                    /*val id= cocineroSeleccionado

                    BaseDatos.tablaCocinero!!.actualizarCocineroFormulario(

                        nombre,
                        edad,
                        costumersScore,
                        fechaIntegracion,
                        autor,
                        id
                    )
*//*
                    cocineroList = BaseDatos.tablaCocinero?.retrieveAllCocineros() ?: emptyList()
                    for (cocinero in cocineroList) {
                        println("ID: ${cocinero.id}")
                        println("Nombre: ${cocinero.nombre}")
                        println("Edad: ${cocinero.edad}")
                        // Print other properties
                        println("-----------------------")
                    }*/
                    //list=cocineroList.toList()
                    consultarCocinero2(adaptador)

                   // irActividad(Cocineros::class.java)

                }
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cocineros)
       //crearDatosPrueba()


/*
        val listView = findViewById<ListView>(R.id.lvCocineros)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            list// Convert ArrayList to List
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

*/
        val listView=findViewById<ListView>(R.id.lvCocineros)
        adaptador =ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BaseDatos.cocinerosFire
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        consultarCocinero2(adaptador)
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
            /*BaseDatos.tablaCocinero!!.eliminarCocineroFormulario(
                id
            )*/
            eliminarRegistro(id)
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
    fun consultarConOrderBy(
        adaptador: ArrayAdapter<Cocinero>
    ){
        val db = Firebase.firestore
        val cocinerosRefUnico=db.collection("cocineros")
        adaptador.notifyDataSetChanged()
        cocinerosRefUnico
            .orderBy("id",Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                for (cocinero in it){
                    cocinero.id
                }
            }
            .addOnFailureListener {  }
    }

    fun limpiarArreglo(){
        BaseDatos.cocinerosFire.clear()
    }

    fun anadirAArregloCocinero(
        cocinero: QueryDocumentSnapshot
    ){

        val nuevoCocinero = Cocinero(
            cocinero.data.get("id") as Int,
            cocinero.data.get("nombre") as String?,
            cocinero.data.get("edad") as Int?,
            cocinero.data.get("costumersScore") as Float?,
            cocinero.data.get("fechaIntegracion") as String?,
            cocinero.data.get("autor") as Boolean?
        )
        BaseDatos.cocinerosFire.add(nuevoCocinero)
    }


    fun consultarCocinero(
        adaptador: ArrayAdapter<Cocinero>
    ){
        val db=Firebase.firestore
        val cocinerosRef = db.collection("cocineros").orderBy("id").limit(1)
        var tarea: Task<QuerySnapshot>?= null
        if (query == null){
            tarea = cocinerosRef.get()
            limpiarArreglo()
            adaptador.notifyDataSetChanged()
        }else{
            tarea = query!!.get()
        }
        if (tarea != null){
            tarea
                .addOnSuccessListener { documentSnapshots ->
                    guardarQuery(documentSnapshots,cocinerosRef)
                    for (cocinero in documentSnapshots){
                        anadirAArregloCocinero(cocinero)
                    }
                    adaptador.notifyDataSetChanged()
                }
                .addOnFailureListener {  }
        }
    }

    fun guardarQuery(
        documentSnapshots: QuerySnapshot,
        refCocineros:Query
    ){
        if(documentSnapshots.size() > 0){
            val ultimoDocumento = documentSnapshots
                .documents[documentSnapshots.size() - 1]
            query = refCocineros
                .startAfter(ultimoDocumento)
        }
    }

    fun eliminarRegistro(id:Int){
        var cadena="${id}"
        val db = Firebase.firestore
        val refCocinero= db.collection("cocineros")
        refCocinero
            .document(cadena)
            .delete()
            .addOnCompleteListener{  }
            .addOnFailureListener {  }
    }

    fun consultarCocinero2(
        adaptador:ArrayAdapter<Cocinero>
    ){
        val db=Firebase.firestore
        val cocinerosRefUnico =db.collection("cocineros")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
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
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener {  }

    }


    fun crearDatosPrueba(){
        val db = Firebase.firestore
        val cocinerosF = db.collection("cocineros")
        var receta1= Receta(1,1,"Arroz",3,15f,"15/8/2023",true,"arroz,ajo","calentar en una olla el ajo hasta que hierva y agregar el arroz")



    }



}