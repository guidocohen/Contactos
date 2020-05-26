package com.guido.contactos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detalle.*

class DetalleActivity : AppCompatActivity() {

    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        setSupportActionBar(toolbarDetalle)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID")!!.toInt()

        mapearDatos()
    }

    private fun mapearDatos() {
        val contacto = MainActivity.obtenerContacto(index)

        tvNombre.text = contacto.nombre.plus(" ").plus(contacto.apellidos)
        tvEmpresa.text = contacto.empresa
        tvEdad.text = contacto.edad.toString().plus(" años")
        tvPeso.text = contacto.peso.toString().plus(" kg")
        tvTelefono.text = contacto.telefono
        tvEmail.text = contacto.email
        tvDireccion.text = contacto.direccion
        ivFotoDetalle.setImageResource(contacto.foto)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.iEditar -> {
                val intent = Intent(this, NuevoActivity::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                true
            }
            R.id.iEliminar -> {
                MainActivity.eliminarContacto(index)
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }

}
