package com.example.schooldrivertcc.view.home.ui.conta


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.schooldrivertcc.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import model.User

class MinhaccFragment : Fragment() {
    private lateinit var textViewNome: TextView
    private lateinit var textViewSobrenome: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewTelefone: TextView
    private lateinit var textViewCPF: TextView
    private lateinit var textViewDataNascimento: TextView
    private lateinit var buttonAtualizar: Button

    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        // Inicializa os TextViews e o Button
        textViewNome = view.findViewById(R.id.textViewNome)
        textViewSobrenome = view.findViewById(R.id.textViewSobrenome)
        textViewEmail = view.findViewById(R.id.textViewEmail)
        textViewTelefone = view.findViewById(R.id.textViewTelefone)
        textViewCPF = view.findViewById(R.id.textViewCPF)
        textViewDataNascimento = view.findViewById(R.id.textViewDataNascimento)
        buttonAtualizar = view.findViewById(R.id.buttonAtualizar)

        // Inicializa a referência do Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("usuarios")

        // Busca os dados do usuário
        buscarDadosUsuario()

        // Configura o botão de atualizar
        buttonAtualizar.setOnClickListener {
            // Aqui você pode implementar a lógica para atualizar os dados
            Toast.makeText(context, "Atualizar lógica não implementada", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun buscarDadosUsuario() {
        val userId = "seu_user_id_aqui" // Substitua pelo ID do usuário que você deseja buscar

        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(User::class.java)
                    user?.let {
                        // Preenche os TextViews com os dados do usuário
                        textViewNome.text = it.nome
                        textViewSobrenome.text = it.sobrenome
                        textViewEmail.text = it.email
                        textViewTelefone.text = it.telefone
                        textViewCPF.text = it.cpf
                        textViewDataNascimento.text = it.dataNascimento
                    }
                } else {
                    Toast.makeText(context, "Usuário não encontrado.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, "Erro ao acessar dados: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
