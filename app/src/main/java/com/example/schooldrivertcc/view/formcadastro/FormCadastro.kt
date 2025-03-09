package com.example.schooldrivertcc.view.formcadastro

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.schooldrivertcc.R
import com.example.schooldrivertcc.databinding.ActivityFormCadastroBinding
import com.example.schooldrivertcc.view.formlogin.FormLogin
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurando o DatePicker para a data de nascimento
        binding.editDataNascimento.setOnClickListener {
            showDatePickerDialog()
        }

        // Formatação do campo de telefone
        binding.editTelefone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().replace("[^\\d]".toRegex(), "")
                val formatted = formatPhoneNumber(input)
                if (formatted != s.toString()) {
                    binding.editTelefone.setText(formatted)
                    binding.editTelefone.setSelection(formatted.length)
                }
            }

            private fun formatPhoneNumber(number: String): String {
                return when (number.length) {
                    in 0..2 -> number // Apenas o DDD
                    in 3..6 -> "(${number.substring(0, 2)}) ${number.substring(2)}"
                    in 7..10 -> "(${number.substring(0, 2)}) ${number.substring(2, 7)}-${number.substring(7)}"
                    else -> "(${number.substring(0, 2)}) ${number.substring(2, 7)}-${number.substring(7, 11)}"
                }
            }
        })

        // Formatação do campo de CPF
        binding.editCpf.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString().replace("[^\\d]".toRegex(), "")
                val formatted = formatCpf(input)
                if (formatted != s.toString()) {
                    binding.editCpf.setText(formatted)
                    binding.editCpf.setSelection(formatted.length)
                }
            }

            private fun formatCpf(cpf: String): String {
                return when (cpf.length) {
                    in 0..3 -> cpf
                    in 4..6 -> "${cpf.substring(0, 3)}.${cpf.substring(3)}"
                    in 7..9 -> "${cpf.substring(0, 3)}.${cpf.substring(3, 6)}.${cpf.substring(6)}"
                    11 -> "${cpf.substring(0, 3)}.${cpf.substring(3, 6)}.${cpf.substring(6, 9)}-${cpf.substring(9)}"
                    else -> cpf
                }
            }
        })

        // Configurando o botão de cadastro
        binding.btCadastrar.setOnClickListener { view ->
            val nome = binding.editNome.text.toString()
            val sobrenome = binding.editSobrenome.text.toString()
            val cpf = binding.editCpf.text.toString().replace("[^\\d]".toRegex(), "") // Remove formatação
            val dataNascimento = binding.editDataNascimento.text.toString()
            val telefone = binding.editTelefone.text.toString()
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            // Validação dos campos
            if (nome.isEmpty() || sobrenome.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty() ||
                telefone.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                showAlert("Preencha todos os campos!", false)
            } else if (!isMaiorDeIdade(dataNascimento)) {
                showAlert("Você deve ter pelo menos 15 anos!", false)
            } else {
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
                    if (cadastro.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        val user = hashMapOf(
                            "nome" to nome,
                            "sobrenome" to sobrenome,
                            "cpf" to cpf,
                            "data_nascimento" to dataNascimento,
                            "telefone" to telefone,
                            "email" to email
                        )

                        // Salvar os dados no Firestore
                        userId?.let {
                            db.collection("usuario")
                                .document(it)
                                .set(user)
                                .addOnSuccessListener {
                                    showAlert("Usuário cadastrado com sucesso!", true)
                                }
                                .addOnFailureListener { e ->
                                    showAlert("Erro ao salvar dados: ${e.message}", false)
                                }
                        }

                        // Limpar campos
                        clearFields()
                    }
                }.addOnFailureListener { exception ->
                    val mensagemErro = when (exception) {
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um e-mail válido"
                        is FirebaseAuthUserCollisionException -> "Este e-mail já está em uso"
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> "Erro: ${exception.message}"
                    }
                    showAlert(mensagemErro, false)
                }
            }
        }
    }

    // Função para mostrar o DatePickerDialog
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
            binding.editDataNascimento.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    // Função para verificar se é maior de 15 anos
    private fun isMaiorDeIdade(dataNascimento: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            val data = sdf.parse(dataNascimento) ?: return false
            val calendar = Calendar.getInstance()
            calendar.time = data

            // Calcular a idade
            val hoje = Calendar.getInstance()
            val idade = hoje.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)

            // Ajuste se o aniversário ainda não ocorreu este ano
            if (hoje.get(Calendar.MONTH) < calendar.get(Calendar.MONTH) ||
                (hoje.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                        hoje.get(Calendar.DAY_OF_MONTH) < calendar.get(Calendar.DAY_OF_MONTH))) {
            }

            idade >= 15 // Verifica se tem pelo menos 15 anos
        } catch (e: Exception) {
            false // Se a data for inválida
        }
    }

    // Função para mostrar um AlertDialog
    private fun showAlert(message: String, isSuccess: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(if (isSuccess) "Sucesso" else "Erro")
        builder.setMessage(message)
        builder.setPositiveButton("Fechar") { dialog, _ ->
            dialog.dismiss()
            if (isSuccess) {
                // Redirecionar para a tela de login
                startActivity(Intent(this, FormLogin::class.java)) // Substitua FormLogin pelo nome correto
                finish()
            }
        }
        builder.show()
    }

    // Limpa os campos após cadastro
    private fun clearFields() {
        binding.editNome.setText("")
        binding.editSobrenome.setText("")
        binding.editCpf.setText("")
        binding.editDataNascimento.setText("")
        binding.editTelefone.setText("")
        binding.editEmail.setText("")
        binding.editSenha.setText("")
    }
}
