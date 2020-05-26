package com.guido.contactos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var lista: ListView
    private lateinit var adaptador: AdaptadorCustom

    companion object {
        var contactos = ArrayList<Contacto>()

        fun agregarContacto(contacto: Contacto) {
            contactos.add(contacto)
        }

        fun obtenerContacto(i: Int): Contacto {
            return contactos[i]
        }

        fun eliminarContacto(i: Int) {
            contactos.removeAt(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        contactos.add(
            Contacto(
                "Marcos", "Rivas", "Contoso", 25,
                70.0F, "Tamaulipas 215", "156529025",
                "marcos@contoso.com", R.drawable.foto_01
            )
        )

        lista = findViewById<ListView>(R.id.lista)
        adaptador = AdaptadorCustom(this, contactos)

        lista.adapter = adaptador

        lista.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, DetalleActivity::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.iNuevo -> {
                val intent = Intent(this, NuevoActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        adaptador.notifyDataSetChanged()
    }
}
