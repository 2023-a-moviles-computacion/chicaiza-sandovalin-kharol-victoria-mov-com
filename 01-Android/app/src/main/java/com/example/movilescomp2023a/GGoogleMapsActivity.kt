package com.example.movilescomp2023a

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class GGoogleMapsActivity : AppCompatActivity() {

    private lateinit var  mapa:GoogleMap
    var permisos=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
    }

    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermiso = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                //permiso que van a checkear
            nombrePermiso
            )
        val tienePermiso = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermiso){
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(nombrePermiso,nombrePermisoCoarse),

            1 // codigo de peticion de los permisos
            )
        }
    }

    fun iniciarLogicaMapa (){
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{googleMap ->
            with(googleMap){
                mapa = googleMap
                establecerConfiguracionMapa()
                moverQuicentro()
            }

        }
    }

    fun moverQuicentro(){
        val zoom = 17f
        val quicentro = LatLng(
            -0.17556708490271092, -78.048014901143776
        )
        val titulo ="Quicentro"
        val markQuicentro = anadirMarcador(quicentro,titulo)
        markQuicentro.tag= titulo
        moverCamaraConZoom(quicentro,zoom)
    }
    fun anadirMarcador(latLng: LatLng, title:String): Marker {
        return mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )!!
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom:Float = 10f){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng,zoom)
        )
    }



    fun establecerConfiguracionMapa(){
        val contexto  = this.applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos){
                mapa.isMyLocationEnabled= true //TENEMOS PERMISOS
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true //defecto
        }
    }
}