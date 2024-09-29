package com.example.schooldrivertcc.view.formlogin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.schooldrivertcc.R
import com.example.schooldrivertcc.databinding.ActivityFormCadastroBinding
import com.example.schooldrivertcc.databinding.ActivityFormLoginBinding
import com.example.schooldrivertcc.view.formcadastro.FormCadastro
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

        private lateinit var binding: ActivityFormLoginBinding
        private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)


        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


           }
        binding.btEntrar.setOnClickListener { view ->


            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()


            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar = com.google.android.material.snackbar.Snackbar.make(
                    view,
                    "Preencha todos os campos!",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
            }

            binding.txtTelaCadastro.setOnClickListener {
                val intent = Intent(this, FormCadastro::class.java)
                startActivity(/* intent = */ intent)
            }
        }

    }

