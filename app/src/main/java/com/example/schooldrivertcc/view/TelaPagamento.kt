package com.example.schooldrivertcc.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.schooldrivertcc.R
import com.example.schooldrivertcc.databinding.ActivityTelaPagamentoBinding
import com.example.schooldrivertcc.view.home.Home

class PagamentoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPagamentoBinding

    // Substitua pelo seu código Pix
    private val codigoPix = "00020126330014BR.GOV.BCB.PIX0111565341178785204000053039865802BR5925Kalleb Jose dos Santos So6009SAO PAULO62140510ZJ1zTX1fFq6304CC50"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPagamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurando a seta de voltar
        val backButton = findViewById<ImageView>(R.id.imageViewBack)
        backButton.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish() // Fecha a atividade atual, se desejado
        }

        // Configurando o botão de pagamento via Pix
        binding.buttonPagarPix.setOnClickListener {
            val intent = Intent(this, TelaPix::class.java)
            startActivity(intent)
        }

        // Configurando o botão de pagamento com cartão de crédito
        binding.buttonCartaoCredito.setOnClickListener {
            // Redirecionar para o Mercado Pago
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mercadopago.com.br"))
            startActivity(intent)
        }
    }

    fun showPixImage() {
        val intent = Intent(this, PixImageActivity::class.java)
        intent.putExtra("codigo_pix", codigoPix)
        startActivity(intent)
    }
}
