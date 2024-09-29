package com.example.schooldrivertcc.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.schooldrivertcc.R
import com.example.schooldrivertcc.view.formlogin.FormLogin

class Negociacaoenviada : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_negociacaoenviada)

        lateinit var btHome: Button

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets




        }
        btHome = findViewById(R.id.bt_ir_home)

        btHome.setOnClickListener{
            val intent = Intent(this,FormLogin::class.java)
            startActivity(intent)
        }
    }
}