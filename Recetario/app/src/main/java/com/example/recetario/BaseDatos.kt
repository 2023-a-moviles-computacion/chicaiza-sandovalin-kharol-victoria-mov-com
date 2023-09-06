package com.example.recetario

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore

class BaseDatos {
    companion object{
        var tablaCocinero:SQLHelperCocinero? = null
        var tablaReceta:SQLHelperRecetas? = null
        var cocinerosFire : ArrayList<Cocinero> = arrayListOf()
        var recetasFire : ArrayList<Receta> = arrayListOf()




    }
}