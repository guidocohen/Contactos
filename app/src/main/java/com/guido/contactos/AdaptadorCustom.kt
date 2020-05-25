package com.guido.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorCustom(var contexto: Context, private var items:ArrayList<Contacto>):BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var viewHolder: ViewHolder
        var vista = convertView

        if (vista == null) {
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto, null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        } else {
            viewHolder = vista.tag as ViewHolder
        }

        val item = getItem(position) as Contacto

        viewHolder.nombre?.text = item.nombre.plus(" ").plus(item.apellido)
        viewHolder.empresa?.text = item.empresa
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
        return this.items.count()!!
    }

    private class ViewHolder(vista: View) {
        var nombre = vista.findViewById<TextView>(R.id.tvNombre)
        var empresa = vista.findViewById<TextView>(R.id.tvEmpresa)
        var foto = vista.findViewById<ImageView>(R.id.ivFoto)
    }
}