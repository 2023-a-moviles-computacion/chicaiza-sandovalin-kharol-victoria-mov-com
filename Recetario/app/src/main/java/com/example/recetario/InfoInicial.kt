package com.example.recetario

class InfoInicial {
    init {
        BaseDatos.tablaReceta!!.crearReceta(2,"Arroz", 3,300.2f,"12-05-2023",true,"arroz, ajo, agua","llenar una olla de agua y cuando hierva agragar el ajo y la sal, dejar reposar por cicno minutos y agregar el arroz a llama baja")
        BaseDatos.tablaReceta!!.crearReceta(3,"Arroz", 3,300.2f,"12-05-2023",true,"arroz, ajo, agua","llenar una olla de agua y cuando hierva agragar el ajo y la sal, dejar reposar por cicno minutos y agregar el arroz a llama baja")
        BaseDatos.tablaReceta!!.crearReceta(5,"Arroz", 3,300.2f,"12-05-2023",true,"arroz, ajo, agua","llenar una olla de agua y cuando hierva agragar el ajo y la sal, dejar reposar por cicno minutos y agregar el arroz a llama baja")

    }
}