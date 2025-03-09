package com.example.schooldrivertcc.view.home.ui.contato

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.schooldrivertcc.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Certifique-se de que o layout tenha um FrameLayout ou similar

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.contatofragment, ContatoFragment())
                .commit()
        }
    }
}

class ContatoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contato, container, false)

        val buttonEmail = view.findViewById<Button>(R.id.button_email)
        val buttonWhatsApp = view.findViewById<Button>(R.id.button_whatsapp)

        buttonEmail.setOnClickListener { enviarEmail() }
        buttonWhatsApp.setOnClickListener { enviarWhatsApp() }

        return view
    }

    private fun enviarEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:kallebjs07@gmail.com") //
            putExtra(Intent.EXTRA_SUBJECT, "Assunto")
            putExtra(Intent.EXTRA_TEXT, "Mensagem")
        }
        if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(emailIntent)
        }
    }

    private fun enviarWhatsApp() {
        val numeroWhatsApp = "5511937293668" //
        val sendIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$numeroWhatsApp"))
        if (sendIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(sendIntent)
        }
    }
}
