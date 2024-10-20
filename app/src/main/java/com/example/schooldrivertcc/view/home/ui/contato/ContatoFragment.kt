package com.example.schooldrivertcc.view.home.ui.contato

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:kallebjs07@gmail.com")
            putExtra(Intent.EXTRA_SUBJECT, "Assunto")
            putExtra(Intent.EXTRA_TEXT, "Mensagem")
        }
        if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(emailIntent)
        }
    }

    private fun enviarWhatsApp() {
        val numeroWhatsApp = "5511937293668"
        val sendIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$numeroWhatsApp"))
        if (sendIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(sendIntent)
        }
    }

    private fun abrirSite() {
        val siteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kallebjose.github.io/SchollSite/"))
        if (siteIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(siteIntent)
        }
    }
}
