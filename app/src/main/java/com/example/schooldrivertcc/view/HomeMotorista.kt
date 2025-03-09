package com.example.schooldrivertcc.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.schooldrivertcc.R

class HomeMotorista : AppCompatActivity() {

    private lateinit var txtTitulo: TextView
    private lateinit var txtHorario: TextView
    private lateinit var btVerRota: Button
    private lateinit var btChat: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_motorista)

        txtTitulo = findViewById(R.id.txt_titulo)
        txtHorario = findViewById(R.id.txt_horario)
        btVerRota = findViewById(R.id.bt_ver_rota)
        btChat = findViewById(R.id.bt_chat)

        // Ação do botão Ver Rota
        btVerRota.setOnClickListener {
            mostrarDialogRota()
        }

        // Ação do botão Ir para a lista de Chats
        btChat.setOnClickListener {
            val intent = Intent(this, SelecionarChatActivity::class.java)
            startActivity(intent)
        }
    }

    private fun mostrarDialogRota() {
        // Criar um dialog para mostrar as rotas
        val dialogView = layoutInflater.inflate(R.layout.dialog_rota, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)

        val dialog = dialogBuilder.create()

        // Configurando a lista de rotas
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

        val listaRotas = dialogView.findViewById<ListView>(R.id.lista_rotas)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rotas)
        listaRotas.adapter = adapter

        // Configurando o botão de fechar
        val btnFechar = dialogView.findViewById<Button>(R.id.btn_fechar)
        btnFechar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
