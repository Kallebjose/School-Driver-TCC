package com.example.schooldrivertcc.view.formlogin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.schooldrivertcc.R
import com.example.schooldrivertcc.databinding.ActivityFormLoginBinding
import com.example.schooldrivertcc.databinding.ActivityLoginMotoristaBinding
import com.example.schooldrivertcc.view.LoginMotorista
import com.example.schooldrivertcc.view.formcadastro.FormCadastro
import com.example.schooldrivertcc.view.home.Home
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        // Verifica se o usuário já está logado
        /* if (auth.currentUser != null) {
            navegarTelaPrincipal()
        } */

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
                val snackbar = Snackbar.make(
                    view,
                    "Preencha todos os campos!",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
                    if (autenticacao.isSuccessful) {
                        navegarTelaPrincipal()
                    }
                }.addOnFailureListener {
                    val snackbar = Snackbar.make(view, "Erro ao tentar fazer login", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }

        binding.txtTelaCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }
        binding.txtMotorista.setOnClickListener {
            navegarParaLoginMotorista() // Chamando o método de navegação
        }
    }

    private fun navegarTelaPrincipal() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
    private fun navegarParaLoginMotorista() {
        val intent = Intent(this, LoginMotorista::class.java)
        startActivity(intent)
        finish()
    }
}
