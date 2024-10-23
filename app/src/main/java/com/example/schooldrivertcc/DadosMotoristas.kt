package com.example.schooldrivertcc

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DadosMotoristas : AppCompatActivity() {

    private lateinit var txtNome: TextView
    private lateinit var txtSobrenome: TextView
    private lateinit var txtCpf: TextView
    private lateinit var txtDataNascimento: TextView
    private lateinit var txtTelefone: TextView
    private lateinit var txtItbDestino: TextView
    private lateinit var txtNumeroPlaca: TextView
    private lateinit var txtCodigoVan: TextView
    private lateinit var btnVoltar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_motoristas)

        // Inicializa as TextViews
        txtNome = findViewById(R.id.txt_nome)
        txtSobrenome = findViewById(R.id.txt_sobrenome)
        txtCpf = findViewById(R.id.txt_cpf)
        txtDataNascimento = findViewById(R.id.txt_data_nascimento)
        txtTelefone = findViewById(R.id.txt_telefone)
        txtItbDestino = findViewById(R.id.txt_itb_destino)
        txtNumeroPlaca = findViewById(R.id.txt_numero_placa)
        txtCodigoVan = findViewById(R.id.txt_codigo_van)
        btnVoltar = findViewById(R.id.bt_voltar)

        // Exemplo de dados
        txtNome.text = "Nome: José Azevedo"
        txtSobrenome.text = "Sobrenome: Azevedo"
        txtCpf.text = "CPF: 123.456.789-00"
        txtDataNascimento.text = "Data de Nascimento: 01/01/1990"
        txtTelefone.text = "Telefone: (11) 91234-5678"
        txtItbDestino.text = "ITB Destino: ITB Professora Maria Theodora Pedreira de Freitas"
        txtNumeroPlaca.text = "Número da Placa: BEE4R20"
        txtCodigoVan.text = "Código da Van: 1020"

        // Configura o listener do botão voltar
        btnVoltar.setOnClickListener {
            finish() // Retorna para a tela anterior
        }
    }
}
