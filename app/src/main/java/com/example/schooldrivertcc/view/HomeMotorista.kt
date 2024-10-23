package com.example.schooldrivertcc.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.example.schooldrivertcc.DadosMotoristas
import com.example.schooldrivertcc.R
import com.example.schooldrivertcc.view.formlogin.FormLogin

class HomeMotorista : AppCompatActivity() {

    private lateinit var txtTitulo: TextView
    private lateinit var txtHorario: TextView
    private lateinit var btVerRota: LinearLayout
    private lateinit var btChat: LinearLayout
    private lateinit var btPerfil: ImageView
    private lateinit var btSair: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_motorista)

        // Inicializa os elementos da UI
        txtTitulo = findViewById(R.id.txt_titulo)
        txtHorario = findViewById(R.id.txt_horario)
        btVerRota = findViewById(R.id.bt_ver_rota)
        btChat = findViewById(R.id.bt_chat)
        btPerfil = findViewById(R.id.bt_perfil)
        btSair = findViewById(R.id.bt_sair)

        // Ação do botão Ver Rota
        btVerRota.setOnClickListener {
            mostrarDialogRota()
        }

        // Ação do botão Ir para a lista de Chats
        btChat.setOnClickListener {
            val intent = Intent(this, SelecionarChatActivity::class.java)
            startActivity(intent)
        }

        // Ação do botão de Perfil
        btPerfil.setOnClickListener {
            val intent = Intent(this, DadosMotoristas::class.java)
            startActivity(intent)
        }

        // Ação do botão Sair
        btSair.setOnClickListener {
            val intent = Intent(this, FormLogin::class.java) // Navega para a tela de login
            startActivity(intent)
            finish() // Fecha a atividade atual
        }
    }

    private fun mostrarDialogRota() {
        val rotas = listOf(
            "1. Engenho Novo - Rua das Flores, 123 - Aluno: Ana Silva",
            "2. Rua São João, 45 - Aluno: Bruno Santos",
            "3. Rua Maria Célia, 78 - Aluno: Carlos Oliveira",
            "4. Rua Joaquim Nabuco, 234 - Aluno: Daniela Pereira",
            "5. Avenida dos Autonomistas, 876 - Aluno: Eduardo Ferreira",
            "6. Rua Monte Castelo, 200 - Aluno: Fernanda Lima",
            "7. Rua do Limão, 15 - Aluno: Gabriel Almeida",
            "8. Rua João de Barro, 32 - Aluno: Helena Costa",
            "9. Avenida São Miguel, 450 - Aluno: Igor Mendes",
            "10. Rua Tietê, 89 - Aluno: Juliana Rocha",
            "11. Rua Francisco de Souza, 167 - Aluno: Lucas Martins",
            "12. Rua dos Eucaliptos, 56 - Aluno: Mariana Ferreira",
            "13. Rua das Palmeiras, 12 - Aluno: Natália Santos",
            "14. ITB - Avenida Brasil, 1200 - Destino final"
        )

        // Criação do dialog para mostrar as rotas
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Rotas")
        dialog.setItems(rotas.toTypedArray(), null) // Exibe as rotas na lista
        dialog.setPositiveButton("OK") { dialogInterface, _ -> dialogInterface.dismiss() }
        dialog.show()
    }
}
