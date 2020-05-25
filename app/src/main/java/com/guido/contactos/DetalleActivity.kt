package com.guido.contactos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detalle.*

class DetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        setSupportActionBar(toolbarDetalle)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
