package com.guido.contactos

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
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
                if (hayCamposVacios()) return true
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
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    fun hayCamposVacios(): Boolean {
        val count = layout_nuevo.childCount
        for (i in 0 until count) {
            if (layout_nuevo.getChildAt(i) is EditText) {
                val editText = layout_nuevo.getChildAt(i) as EditText

                if (editText.text.toString().isEmpty()) {
                    Toast.makeText(this, "Debe completar todos los campos", LENGTH_SHORT).show()
                    return true
                }
            }
        }
        return false
    }
}
