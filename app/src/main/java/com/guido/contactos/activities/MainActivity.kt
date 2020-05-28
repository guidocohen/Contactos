package com.guido.contactos.activities

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.guido.contactos.R
import com.guido.contactos.adapters.AdaptadorCustom
import com.guido.contactos.adapters.AdaptadorGrid
import com.guido.contactos.adapters.AdaptadorList
import com.guido.contactos.models.Contacto
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var contactos = ArrayList<Contacto>()
        private lateinit var adaptador: AdaptadorCustom

        fun agregarContacto(contacto: Contacto) {
            adaptador.addItem(contacto)
        }

        fun obtenerContacto(i: Int): Contacto {
            return adaptador.getItem(i) as Contacto
        }

        fun eliminarContacto(index: Int) {
            adaptador.removeItem(index)
        }

        fun actualizarContacto(index: Int, nuevoContacto: Contacto) {
            adaptador.updateItem(index, nuevoContacto)
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

        contactos.add(
            Contacto(
                "Licha", "Lopez", "Facebook", 35,
                70.0F, "Perón 2215", "32523",
                "licha@lopez.com", R.drawable.foto_02
            )
        )

        contactos.add(
            Contacto(
                "Diego", "Milito", "Google", 33,
                75.1F, "Baquistata 1245", "64535561",
                "diego@milito.com", R.drawable.foto_03
            )
        )

        adaptador = AdaptadorList(this, contactos)
        listView.adapter = adaptador

        gridView.adapter = AdaptadorGrid(this, contactos)

        listView.setOnItemClickListener { _, _, position, _ ->
            goToActivity(position)
        }
        gridView.setOnItemClickListener { _, _, position, _ ->
            goToActivity(position)
        }
    }

    private fun goToActivity(position: Int) {
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra("ID", position.toString())
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        menu.findItem(R.id.switchView).setActionView(R.layout.switch_item)
            .actionView.findViewById<Switch>(R.id.sCambiaVista)
            .setOnCheckedChangeListener { _, _ ->
                viewSwitcher.showNext()
                if (listView.visibility == View.VISIBLE) {
                    adaptador = AdaptadorList(this, contactos)
                    listView.adapter = adaptador
                } else {
                    adaptador = AdaptadorGrid(this, contactos)
                    gridView.adapter = adaptador
                }
            }

        val searchManager =
            this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.searchView)?.actionView as SearchView).apply {
            queryHint = "Buscar contacto..."
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    adaptador.filter.filter(newText!!)
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
            })
        }

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

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
