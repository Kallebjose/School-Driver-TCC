package com.example.schooldrivertcc.view


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.schooldrivertcc.R
import com.example.schooldrivertcc.databinding.ActivityPixImageBinding

class PixImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPixImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPixImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val codigoPix = intent.getStringExtra("codigo_pix") ?: return

        // Exibir o código Pix em um TextView
        binding.textViewCodigoPix.text = codigoPix

        // Configurar a imagem do código Pix (substitua pelo seu recurso)
        binding.imageViewPix.setImageResource(R.drawable.qrcode) // Adicione sua imagem aqui

        // Copiar o código Pix para a área de transferência
        binding.buttonCopiar.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Código Pix", codigoPix)
            clipboard.setPrimaryClip(clip)
        }
    }
}