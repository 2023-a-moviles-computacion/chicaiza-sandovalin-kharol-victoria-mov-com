package com.example.recetario

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class cocineros_recicler_adaptador(
    private val contexto : Cocineros,
     private val lista : ArrayList<Cocinero>,
    private val recyclerView: RecyclerView
    ): RecyclerView.Adapter<cocineros_recicler_adaptador.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}