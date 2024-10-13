package com.example.schooldrivertcc.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.schooldrivertcc.R

class TelaNegociar : AppCompatActivity() {

    private lateinit var editLocalizacao: EditText
    private lateinit var radioGroup1: RadioGroup
    private lateinit var radioGroup2: RadioGroup
    private lateinit var radioGroup3: RadioGroup
    private lateinit var radioGroup4: RadioGroup
    private lateinit var buttonEnviar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_negociar)

        editLocalizacao = findViewById(R.id.edit_localizacao)
        radioGroup1 = findViewById(R.id.radio_group1)
        radioGroup2 = findViewById(R.id.radio_group2)
        radioGroup3 = findViewById(R.id.radio_group3)
        radioGroup4 = findViewById(R.id.radio_group4)
        buttonEnviar = findViewById(R.id.button_enviar)

        buttonEnviar.setOnClickListener {
            enviarNegociacao()
        }
    }

    private fun enviarNegociacao() {
        val localizacao = editLocalizacao.text.toString()

        // Verifica se todos os campos foram preenchidos
        if (localizacao.isBlank() ||
            radioGroup1.checkedRadioButtonId == -1 ||
            radioGroup2.checkedRadioButtonId == -1 ||
            radioGroup3.checkedRadioButtonId == -1 ||
            radioGroup4.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val op1 = findViewById<RadioButton>(radioGroup1.checkedRadioButtonId).text
        val op2 = findViewById<RadioButton>(radioGroup2.checkedRadioButtonId).text
        val op3 = findViewById<RadioButton>(radioGroup3.checkedRadioButtonId).text
        val op4 = findViewById<RadioButton>(radioGroup4.checkedRadioButtonId).text

        val mensagem = "Negociação:\n" +
                "Localização: $localizacao\n" +
                "Grupo 1: $op1\n" +
                "Grupo 2: $op2\n" +
                "Grupo 3: $op3\n" +
                "Grupo 4: $op4"

        val numero = "5511937293668"
        val url = "https://api.whatsapp.com/send?phone=$numero&text=${Uri.encode(mensagem)}"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)

        try {
            startActivity(intent)
            Toast.makeText(this, "Negociação enviada com sucesso!", Toast.LENGTH_SHORT).show()
            finish() // Encerra a atividade atual
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao enviar Negociação.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Adicione aqui o código para voltar ao HomeFragment
    }
}
