package com.guido.contactos.adapters

import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.guido.contactos.models.Contacto
import java.util.*
import kotlin.collections.ArrayList

abstract class AdaptadorCustom(
    private var items: ArrayList<Contacto>
) :
    BaseAdapter(), Filterable {

    private var copiaItems = items

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
        items.add(item)
        notifyDataSetChanged()
    }

    fun updateItem(index: Int, newItem: Contacto, query: String) {
        copiaItems[(copiaItems.indexOf(items[index]))] = newItem
        items[index] = newItem
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        val item = items[index]
        copiaItems.remove(item)
        items.remove(item)
        notifyDataSetChanged()
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
                                .plus(" ")
                                .plus(row.apellidos.toLowerCase(Locale.ROOT))
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