package com.example.movilescomp2023a

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

// en AppCompatActivity va a estar toda la logica que vemos aqui
class MainActivity : AppCompatActivity() {

    val callbackContenidoCIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode== Activity.RESULT_OK){
                if(result.data !=null){
                    val data= result.data
                    "${data?.getStringExtra("nombreModificado")}"
                }
            }
        }

    //callback para que devuelva un contacto del telefono, es mas dificil
    val callbackIntentPickUri =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if (result.resultCode === RESULT_OK){
                if(result.data != null){
                    if(result.data!!.data !=null){
                        val uri: Uri =result.data!!.data!!
                        val cursor = contentResolver.query(uri,null,null,null,null,null)
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        "Telefono ${telefono}"
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida
            .setOnClickListener {
                irActividad(AACicloVida::class.java)
            }
        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView
            .setOnClickListener {
                irActividad(BListView::class.java)
            }

        //funciones para boton intend implicito y explicito
        val botonIntentImplicito= findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito
            .setOnClickListener{
                val intentConRespuesta = Intent(
                    Intent.ACTION_PICK,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                )
                callbackIntentPickUri.launch(intentConRespuesta)
            }

        val botonIntentExplicito =findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExplicito
            .setOnClickListener{
                abrirActividadConParametros(CIntentExplicitoParametros::class.java)
            }
    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplito= Intent(this,clase)
        //Enviar par[ametros (solamente variables primitivas)
        //esto va hacia la clase CIntentExplicitoParametros
        intentExplito.putExtra("nombre","Kharol")
        intentExplito.putExtra("apellido","Chicaiza")
        intentExplito.putExtra("edad",34)

        callbackContenidoCIntentExplicito.launch(intentExplito)

    }

    fun irActividad(
    clase: Class<*>
    ){
        val intent=Intent(this,clase)
        startActivity(intent)
    }
}