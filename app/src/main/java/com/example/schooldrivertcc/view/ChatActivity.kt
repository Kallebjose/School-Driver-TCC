package com.example.schooldrivertcc.view

import Message
import MessageAdapter
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.schooldrivertcc.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class ChatActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var txtChatName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)

        // Inicializar o Firestore
        firestore = FirebaseFirestore.getInstance()

        // Configurar UI
        txtChatName = findViewById(R.id.pirueiro_n)
        val messageInput = findViewById<EditText>(R.id.message_i)
        val sendButton = findViewById<Button>(R.id.send_n)
        val listView = findViewById<ListView>(R.id.message_l)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)
        listView.adapter = messageAdapter

        // Definir o nome do chat
        val chatName = intent.getStringExtra("CHAT_NAME") ?: "Chat"
        txtChatName.text = "Chat com: $chatName"

        // Enviar mensagem
        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString()
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)  // Chamando a função com um único argumento
                messageInput.text.clear()
            }
        }

        // Ouvir novas mensagens
        listenForMessages()

        // Aplicar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Função para enviar mensagem
    private fun sendMessage(messageText: String) {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val message = Message(messageText, currentUserId ?: "", System.currentTimeMillis())
        firestore.collection("messages").add(message).addOnSuccessListener {
            // Mensagem enviada com sucesso
        }.addOnFailureListener { e ->
            // Lidar com falha ao enviar a mensagem
            e.printStackTrace()
        }
    }

    // Função para ouvir mensagens no Firestore
    private fun listenForMessages() {
        firestore.collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) {
                    return@addSnapshotListener
                }
                messageList.clear()
                for (doc in snapshot.documents) {
                    val message = doc.toObject(Message::class.java)
                    message?.let { messageList.add(it) }
                }
                messageAdapter.notifyDataSetChanged()
            }
    }
}
