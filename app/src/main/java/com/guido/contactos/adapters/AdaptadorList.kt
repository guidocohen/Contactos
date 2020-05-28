package com.guido.contactos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.guido.contactos.R
import com.guido.contactos.models.Contacto

class AdaptadorList(private var contexto: Context, private var items: ArrayList<Contacto>) :
    AdaptadorCustom(contexto, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        lateinit var viewHolder: ViewHolder
        var vista = convertView

        if (vista == null) {
            vista = LayoutInflater.from(contexto).inflate(R.layout.template_contacto, null)
            viewHolder =
                ViewHolder(vista)
            vista.tag = viewHolder
        } else {
            viewHolder = vista.tag as ViewHolder
        }

        val item = getItem(position) as Contacto

        viewHolder.nombre?.text = item.nombre.plus(" ").plus(item.apellidos)
        viewHolder.empresa?.text = item.empresa
        viewHolder.foto?.setImageResource(item.foto)

        return vista!!
    }

    private class ViewHolder(vista: View) {
        var nombre = vista.findViewById<TextView>(R.id.tvNombre)
        var empresa = vista.findViewById<TextView>(R.id.tvEmpresa)
        var foto = vista.findViewById<ImageView>(R.id.ivFoto)
    }
}