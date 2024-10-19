package com.example.schooldrivertcc.view.formcadastro

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
            val cpf = binding.editCpf.text.toString().replace("[^\\d]".toRegex(), "")
            val dataNascimento = binding.editDataNascimento.text.toString()
            val telefone = binding.editTelefone.text.toString().replace("[^\\d]".toRegex(), "") // Remove a formatação para validação
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val confirmSenha = binding.editConfirmSenha.text.toString() // Campo de confirmação de senha

            // Validação dos campos
            if (nome.isEmpty()) {
                binding.editNome.error = "Preencha o campo nome!"
            } else if (!isValidName(nome)) {
                binding.editNome.error = "O nome não pode conter números!"
            }

            if (sobrenome.isEmpty()) {
                binding.editSobrenome.error = "Preencha o campo sobrenome!"
            } else if (!isValidName(sobrenome)) {
                binding.editSobrenome.error = "O sobrenome não pode conter números!"
            }

            if (cpf.isEmpty()) {
                binding.editCpf.error = "Preencha o campo CPF!"
            } else if (!isValidCPF(cpf)) {
                binding.editCpf.error = "Digite um CPF válido!"
            }

            if (dataNascimento.isEmpty()) {
                binding.editDataNascimento.error = "Preencha a data de nascimento!"
            }

            if (telefone.isEmpty()) {
                binding.editTelefone.error = "Preencha o telefone!"
            } else if (!isValidPhoneNumber(telefone)) {
                binding.editTelefone.error = "Digite um número de telefone válido!"
            }

            if (email.isEmpty()) {
                binding.editEmail.error = "Preencha o campo e-mail!"
            } else if (!isValidEmail(email)) {
                binding.editEmail.error = "Digite um e-mail válido!"
            }

            if (senha.isEmpty()) {
                binding.editSenha.error = "Preencha o campo senha!"
            } else if (senha.length < 6) {
                binding.editSenha.error = "A senha deve ter no mínimo 6 caracteres!"
            }

            if (confirmSenha.isEmpty()) {
                binding.editConfirmSenha.error = "Confirme sua senha!"
            } else if (senha != confirmSenha) {
                binding.editConfirmSenha.error = "As senhas não coincidem!"
            }

            // Verifica se não há erros antes de tentar cadastrar
            if (binding.editNome.error == null && binding.editSobrenome.error == null &&
                binding.editCpf.error == null  &&
                binding.editTelefone.error == null && binding.editEmail.error == null &&
                binding.editSenha.error == null && binding.editConfirmSenha.error == null) {

                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { cadastro ->
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

                            clearFields()
                        }
                    }
                    .addOnFailureListener { exception ->
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

    // Função para limpar os campos após o cadastro
    private fun clearFields() {
        binding.editNome.setText("")
        binding.editSobrenome.setText("")
        binding.editCpf.setText("")
        binding.editDataNascimento.setText("")
        binding.editTelefone.setText("")
        binding.editEmail.setText("")
        binding.editSenha.setText("")
        binding.editConfirmSenha.setText("")
    }

    // Verificação básica de e-mail
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Validação básica de CPF
    private fun isValidCPF(cpf: String): Boolean {
        return cpf.length == 11 // Considera apenas os dígitos
    }

    // Verificação de telefone
    private fun isValidPhoneNumber(telefone: String): Boolean {
        return telefone.length == 11 // Considerando apenas os dígitos
    }

    // Verificação do nome (não pode conter números)
    private fun isValidName(name: String): Boolean {
        return name.matches(Regex("^[a-zA-Z\\s]+$"))
    }

    // Função para exibir alertas
    private fun showAlert(mensagem: String, sucesso: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(if (sucesso) "Sucesso" else "Erro")
        builder.setMessage(mensagem)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            if (sucesso) {
                startActivity(Intent(this, FormLogin::class.java))
                finish()
            }
        }
        builder.show()
    }
}
