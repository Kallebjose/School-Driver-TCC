package com.example.schooldrivertcc.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.schooldrivertcc.R
import com.example.schooldrivertcc.databinding.ActivityTelaPixBinding
import com.example.schooldrivertcc.view.home.Home
import com.example.schooldrivertcc.view.home.ui.home.HomeFragment

class TelaPix : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPixBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPixBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar o Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrowback) // ícone de voltar

        binding.buttonCopiarCodigo.setOnClickListener {
            copiarCodigoPix()
        }
    }

    private fun copiarCodigoPix() {
        val codigoPix = intent.getStringExtra("codigo_pix") ?: return
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Código PIX", codigoPix)
        clipboard.setPrimaryClip(clip)

        // Mensagem de confirmação
        Toast.makeText(this, "Código PIX copiado para a área de transferência", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish() // Finaliza a TelaPix
        return true
    }
}
