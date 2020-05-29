package com.guido.contactos.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, ContactosActivity::class.java)
        intent.action = Intent.ACTION_SEARCH
        startActivity(intent)
    }
}