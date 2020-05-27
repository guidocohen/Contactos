package com.guido.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class AdaptadorCustomGrid(private var contexto: Context, private var items: ArrayList<Contacto>) :
    BaseAdapter(), Filterable {

    private var copiaItems = items

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var viewHolder: ViewHolder
        var vista = convertView

        if (vista == null) {
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto_grid, null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        } else {
            viewHolder = vista.tag as ViewHolder
        }

        val item = getItem(position) as Contacto

        viewHolder.nombre?.text = item.nombre.plus(" ").plus(item.apellidos)
        viewHolder.foto?.setImageResource(item.foto)

        return vista!!
    }

    override fun getItem(position: Int): Any {
        return this.items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.items.count()
    }

    fun addItem(item: Contacto) {
        copiaItems.add(item)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun updateItem(index: Int, newItem: Contacto) {
        copiaItems[index] = newItem
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        copiaItems.removeAt(index)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    private class ViewHolder(vista: View) {
        var nombre = vista.findViewById<TextView>(R.id.tvNombre)
        var foto = vista.findViewById<ImageView>(R.id.ivFoto)
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString().trim()
                items = if (charString.isEmpty()) {
                    ArrayList(copiaItems)
                } else {
                    val filteredList = ArrayList<Contacto>()
                    for (row in copiaItems) {
                        if (row.nombre.toLowerCase(Locale.ROOT)
                                .contains(charString.toLowerCase(Locale.ROOT))
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = items
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                items = filterResults.values as ArrayList<Contacto>
                notifyDataSetChanged()
            }
        }
    }
}
