package com.example.schooldrivertcc.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.schooldrivertcc.R

class Negociacao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var btEnviada: Button

        enableEdgeToEdge()
        setContentView(R.layout.activity_negociacao)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        btEnviada = findViewById(R.id.bt_enviada)

        btEnviada.setOnClickListener {

            val intent = Intent(this,Negociacaoenviada::class.java)
            startActivity(intent)
        }

    }
}