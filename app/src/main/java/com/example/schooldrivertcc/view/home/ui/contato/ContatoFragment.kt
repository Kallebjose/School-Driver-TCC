package com.example.schooldrivertcc.view.home.ui.contato

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.schooldrivertcc.R

class ContatoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contato, container, false)

        val buttonEmail = view.findViewById<LinearLayout>(R.id.button_email)
        val buttonWhatsApp = view.findViewById<LinearLayout>(R.id.button_whatsapp)
        val buttonWebsite = view.findViewById<LinearLayout>(R.id.button_website)

        buttonEmail.setOnClickListener { enviarEmail() }
        buttonWhatsApp.setOnClickListener { enviarWhatsApp() }
        buttonWebsite.setOnClickListener { abrirSite() }

        return view
    }

    private fun enviarEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822" // Tipo MIME para e-mails
            putExtra(Intent.EXTRA_EMAIL, arrayOf("kallebjs07@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Assunto")
            putExtra(Intent.EXTRA_TEXT, "Mensagem")
        }

        // Verifica se há um aplicativo que pode enviar o e-mail
        try {
            if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(Intent.createChooser(emailIntent, "Escolha um cliente de e-mail:"))
            } else {
                Toast.makeText(requireContext(), "Nenhum aplicativo de e-mail disponível", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Erro ao tentar abrir o aplicativo de e-mail", Toast.LENGTH_SHORT).show()
            e.printStackTrace() // Para depuração
        }
    }


    private fun enviarWhatsApp() {
        val numeroWhatsApp = "5511937293668"
        val sendIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$numeroWhatsApp"))

        // Verifica se o WhatsApp está instalado
        try {
            startActivity(sendIntent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "WhatsApp não está instalado ou ocorreu um erro ao abrir", Toast.LENGTH_SHORT).show()
            e.printStackTrace() // Para depuração
        }
    }

    private fun abrirSite() {
        val siteUrl = "https://kallebjose.github.io/SchollSite/"
        val siteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(siteUrl))

        // Tente abrir o site
        try {
            startActivity(siteIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Nenhum navegador disponível para abrir o site", Toast.LENGTH_SHORT).show()
            e.printStackTrace() // Para depuração
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Erro ao tentar abrir o site", Toast.LENGTH_SHORT).show()
            e.printStackTrace() // Para depuração
        }
    }


}
