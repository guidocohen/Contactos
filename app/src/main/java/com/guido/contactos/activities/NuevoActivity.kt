package com.guido.contactos.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.guido.contactos.R
import com.guido.contactos.models.Contacto
import kotlinx.android.synthetic.main.activity_nuevo.*

class NuevoActivity : AppCompatActivity() {

    private var fotoIndex: Int = 0
    private var fotos = arrayOf(
        R.drawable.foto_01,
        R.drawable.foto_02,
        R.drawable.foto_03,
        R.drawable.foto_04,
        R.drawable.foto_05,
        R.drawable.foto_06
    )
    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        setSupportActionBar(toolbarNuevo)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        ivFotoNueva.setOnClickListener {
            seleccionarFoto()
        }

        // Reconocer acción de nuevo vs editar
        if (intent.hasExtra("ID")) {
            index = intent.getStringExtra("ID")!!.toInt()
            rellenarDatos(index)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.iCrearNuevo -> {
                if (hayCamposVacios()) return true
                val contacto = Contacto(
                    etNombre.text.toString(),
                    etApellidos.text.toString(),
                    etEmpresa.text.toString(),
                    etEdad.text.toString().toInt(),
                    etPeso.text.toString().toFloat(),
                    etDireccion.text.toString(),
                    etTelefono.text.toString(),
                    etEmail.text.toString(),
                    obtenerFoto(fotoIndex)
                )
                if (index > -1) MainActivity.actualizarContacto(
                    index,
                    contacto
                )
                else MainActivity.agregarContacto(
                    contacto
                )
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun hayCamposVacios(): Boolean {
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

    private fun seleccionarFoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona imagen de perfil")
        val entries =
            ArrayList<String>(
                listOf(
                    "Foto 01",
                    "Foto 02",
                    "Foto 03",
                    "Foto 04",
                    "Foto 05",
                    "Foto 06"
                )
            )
        val dialogAdapter =
            ArrayAdapter(this, android.R.layout.simple_selectable_list_item, entries)

        builder.setAdapter(dialogAdapter) { _, which ->
            fotoIndex = which
            ivFotoNueva.setImageResource(obtenerFoto(fotoIndex))
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun obtenerFoto(index: Int): Int {
        return fotos[index]
    }

    private fun rellenarDatos(index: Int) {
        val contacto =
            MainActivity.obtenerContacto(index)

        etNombre.setText(contacto.nombre)
        etApellidos.setText(contacto.apellidos)
        etEmpresa.setText(contacto.empresa)
        etEdad.setText(contacto.edad.toString())
        etPeso.setText(contacto.peso.toString())
        etTelefono.setText(contacto.telefono)
        etEmail.setText(contacto.email)
        etDireccion.setText(contacto.direccion)

        ivFotoNueva.setImageResource(contacto.foto)

        for ((posicion, foto) in fotos.withIndex()) {
            if (contacto.foto == foto) {
                fotoIndex = posicion
            }
        }
    }

    
}
