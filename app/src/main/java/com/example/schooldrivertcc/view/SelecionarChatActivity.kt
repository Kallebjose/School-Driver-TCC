package com.example.schooldrivertcc.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.schooldrivertcc.R

class SelecionarChatActivity : AppCompatActivity() {

    private lateinit var listViewChats: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecionar_chat)

        listViewChats = findViewById(R.id.listViewChats)

        // Lista de chats (adicione mais nomes de chats conforme necessário)
        val chats = listOf("Igor Mendes")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chats)
        listViewChats.adapter = adapter

        // Ação ao clicar no chat
        listViewChats.setOnItemClickListener { _, _, position, _ ->
            val chatName = chats[position]
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("CHAT_NAME", chatName)
            }
            startActivity(intent)
        }
    }
}
