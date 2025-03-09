package com.example.schooldrivertcc.view.home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import com.example.schooldrivertcc.R
import androidx.appcompat.app.AlertDialog
import com.example.schooldrivertcc.view.ChatActivity
import com.example.schooldrivertcc.view.PagamentoActivity
import com.example.schooldrivertcc.view.TelaNegociar

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Acesse o botão de chat
        val chatButton = view.findViewById<Button>(R.id.button_chat)
        chatButton.setOnClickListener {
            val intent = Intent(activity, ChatActivity::class.java)
            startActivity(intent)
        }

        // Referências para os CardViews
        val cardVan1020 = view.findViewById<CardView>(R.id.card_van1)
        val cardVan3040 = view.findViewById<CardView>(R.id.card_van2)
        val cardVan5060 = view.findViewById<CardView>(R.id.card_van3)
        val cardVan7080 = view.findViewById<CardView>(R.id.card_van4)

        // Descrições das vans
        val descricaoVan1020 = """
            Nome do motorista: José Azevedo
            Código da Van: 1020
            ITB Destino: ITB Professora Maria Theodora Pedreira de Freitas
            Número da Placa: BEE4R20
            Ano de Fabricação: 2018
            Cor do Veículo: Prata
            Número de Assentos: 21
            Condições do Veículo: Semi-novo
            Sistema de Segurança: GPS, Câmera
            Licenciamento: 2024
            Responsável pela Van: Andre Guerlando
            Email: andreguerlando@gmail.com
        """.trimIndent()

        val descricaoVan3040 = """
            Nome do motorista: Gabriel Silva
            Código da Van: 3040
            ITB Destino: ITB Professor Munir José
            Número da Placa: UDT1H56
            Ano de Fabricação: 2019
            Cor do Veículo: Preto
            Número de Assentos: 15
            Condições do Veículo: Semi-novo
            Sistema de Segurança: GPS
            Licenciamento: 2024
            Responsável pela Van: Kalleb José
            Email: kallebjs@gmail.com
        """.trimIndent()

        val descricaoVan5060 = """
            Nome do motorista: Antonio Souza
            Código da Van: 5060
            ITB Destino: Profª Maria Sylvia Chaluppe Mello e ITB Brasílio Flores de Azevedo
            Número da Placa: JSQ6L97
            Ano de Fabricação: 2023
            Cor do Veículo: Branco
            Número de Assentos: 20
            Condições do Veículo: Novo
            Sistema de Segurança: GPS, Vidro blindado
            Licenciamento: 2024
            Responsável pela Van: Guilherme Gomes
            Email: guilhermegomes@gmail.com
        """.trimIndent()

        val descricaoVan7080 = """
            Nome do motorista: Maria Helena
            Código da Van: 7080
            ITB Destino: ITB Brasílio Flores de Azevedo
            Número da Placa: GPA3Q46
            Ano de Fabricação: 2022
            Cor do Veículo: Prata
            Número de Assentos: 21
            Condições do Veículo: Semi-novo
            Sistema de Segurança: GPS
            Licenciamento: 2024
            Responsável pela Van: Laryssa Barros
            Email: larybarros@gmail.com
        """.trimIndent()

        // Configurar cliques para os CardViews
        cardVan1020.setOnClickListener { mostrarDescricaoCompleta("Van 1020", descricaoVan1020) }
        cardVan3040.setOnClickListener { mostrarDescricaoCompleta("Van 3040", descricaoVan3040) }
        cardVan5060.setOnClickListener { mostrarDescricaoCompleta("Van 5060", descricaoVan5060) }
        cardVan7080.setOnClickListener { mostrarDescricaoCompleta("Van 7080", descricaoVan7080) }

        // Acesse os botões de Negociar e Pagamento
        val buttonNegociar = view.findViewById<Button>(R.id.button_negociar)
        val buttonPagamento = view.findViewById<Button>(R.id.button_pagamento)

        buttonNegociar.setOnClickListener {
            // Navegar para a TelaNegociar
            val intent = Intent(requireContext(), TelaNegociar::class.java)
            startActivity(intent)
        }

        buttonPagamento.setOnClickListener {
            // Navegar para a PagamentoActivity
            val intent = Intent(requireContext(), PagamentoActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun mostrarDescricaoCompleta(van: String, descricao: String) {
        // Exibir a descrição completa em um AlertDialog
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Detalhes da $van")
        dialogBuilder.setMessage(descricao)
        dialogBuilder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}
