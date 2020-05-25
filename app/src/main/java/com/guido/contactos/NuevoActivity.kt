package com.guido.contactos

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nuevo.*

class NuevoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        setSupportActionBar(toolbarNuevo)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.iCrearNuevo -> {
                MainActivity.agregarContacto(
                    Contacto(
                        etNombre.text.toString(),
                        etApellidos.text.toString(),
                        etEmpresa.text.toString(),
                        etEdad.text.toString().toInt(),
                        etPeso.text.toString().toFloat(),
                        etDireccion.text.toString(),
                        etTelefono.text.toString(),
                        etEmail.text.toString(),
                        R.drawable.foto_02
                    )
                )
                finish()
                Log.d("NO ELEMENTOS", MainActivity.contactos.count().toString())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
