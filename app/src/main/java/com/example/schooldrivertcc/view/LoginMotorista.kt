package com.example.schooldrivertcc.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.schooldrivertcc.R
import com.example.schooldrivertcc.databinding.ActivityLoginMotoristaBinding
import com.example.schooldrivertcc.view.HomeMotorista
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginMotorista : AppCompatActivity() {

    private lateinit var binding: ActivityLoginMotoristaBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginMotoristaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Configurando o clique do botão de login
        binding.btEntrarM.setOnClickListener { view ->
            val email = binding.editEmailM.text.toString()
            val senha = binding.editSenhaM.text.toString()

            // Validação de campos vazios
            if (email.isEmpty() || senha.isEmpty()) {
                showSnackbar(view, "Preencha todos os campos!")
                return@setOnClickListener
            }

            // Validação de formato de email
            if (!isValidEmail(email)) {
                showSnackbar(view, "Email inválido!")
                return@setOnClickListener
            }

            // Verificando se o email é o do motorista
            if (email != "motorista1@gmail.com") {
                showSnackbar(view, "Você não é um motorista!")
                return@setOnClickListener
            }

            // Autenticação com Firebase
            auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
                if (autenticacao.isSuccessful) {
                    navegarTelaPrincipal()
                } else {
                    showSnackbar(view, "Erro ao tentar fazer login")
                }
            }.addOnFailureListener {
                showSnackbar(view, "Erro ao tentar fazer login")
            }
        }
    }

    // Função para mostrar Snackbar
    private fun showSnackbar(view: android.view.View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }

    // Função para validar o formato do email
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    // Navegar para a tela principal
    private fun navegarTelaPrincipal() {
        val intent = Intent(this, HomeMotorista::class.java)
        startActivity(intent)
        finish()
    }
}
