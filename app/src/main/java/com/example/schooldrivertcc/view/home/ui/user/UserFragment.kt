package com.example.schooldrivertcc.view.home.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.schooldrivertcc.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)

        // Carregar os dados do usuário ao abrir a tela
        carregarDadosDoUsuario()

        // Ação para o botão de atualização
        binding.buttonAtualizar.setOnClickListener {
            atualizarDadosDoUsuario()
        }

        return binding.root
    }

    private fun carregarDadosDoUsuario() {
        if (userId != null) {
            db.collection("usuario").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Carregar os dados nos TextViews
                        binding.textViewNome.text = "Nome: ${document.getString("nome") ?: "N/A"}"
                        binding.textViewSobrenome.text = "Sobrenome: ${document.getString("sobrenome") ?: "N/A"}"
                        binding.textViewEmail.setText(document.getString("email") ?: "")
                        binding.textViewTelefone.setText(document.getString("telefone") ?: "")
                        binding.textViewCPF.text = "CPF: ${document.getString("cpf") ?: "N/A"}"
                        binding.textViewDataNascimento.text = "Data de Nascimento: ${document.getString("data_nascimento") ?: "N/A"}"
                    } else {
                        Toast.makeText(context, "Usuário não encontrado.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Erro ao carregar dados: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Usuário não autenticado.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun atualizarDadosDoUsuario() {
        val emailAtualizado = binding.textViewEmail.text.toString()
        val telefoneAtualizado = binding.textViewTelefone.text.toString()

        if (userId != null) {
            val userUpdates = mapOf(
                "email" to emailAtualizado,
                "telefone" to telefoneAtualizado
            )

            db.collection("usuario").document(userId).update(userUpdates)
                .addOnSuccessListener {
                    Toast.makeText(context, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Erro ao atualizar dados: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
