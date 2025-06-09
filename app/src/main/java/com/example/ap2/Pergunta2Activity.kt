package com.example.ap2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Pergunta2Activity : AppCompatActivity() {

    private lateinit var textViewPergunta: TextView
    private lateinit var radioGroupOpcoes: RadioGroup
    private lateinit var btnProxima: Button

    // Variáveis para guardar os dados do usuário e respostas anteriores
    private var nomeUsuario: String? = null
    private var idadeUsuario: Int = -1
    private var respostaPergunta1: String? = null

    // Variável para guardar a resposta desta pergunta
    private var respostaPergunta2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergunta2)

        // Recuperar dados da Pergunta1Activity
        nomeUsuario = intent.getStringExtra("NOME_USUARIO")
        idadeUsuario = intent.getIntExtra("IDADE_USUARIO", -1)
        respostaPergunta1 = intent.getStringExtra("RESPOSTA_PERGUNTA_1")

        // Exibir um Toast para depuração (opcional)
        // Toast.makeText(this, "P1: $respostaPergunta1", Toast.LENGTH_SHORT).show()

        textViewPergunta = findViewById(R.id.textViewPergunta2)
        radioGroupOpcoes = findViewById(R.id.radioGroupPergunta2)
        btnProxima = findViewById(R.id.btnProximaPergunta2)

        // O texto da pergunta já está no XML, mas você poderia configurá-lo dinamicamente se quisesse
        // textViewPergunta.text = "Sua pergunta para a Pergunta 2 aqui..."

        btnProxima.setOnClickListener {
            val selectedOptionId = radioGroupOpcoes.checkedRadioButtonId
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Por favor, selecione uma opção.", Toast.LENGTH_SHORT).show()
            } else {
                val radioButtonSelecionado = findViewById<RadioButton>(selectedOptionId)
                respostaPergunta2 = radioButtonSelecionado.text.toString()

                // Navegar para a Pergunta3Activity
                val intent = Intent(this, Pergunta3Activity::class.java)
                intent.putExtra("NOME_USUARIO", nomeUsuario)
                intent.putExtra("IDADE_USUARIO", idadeUsuario)
                intent.putExtra("RESPOSTA_PERGUNTA_1", respostaPergunta1)
                intent.putExtra("RESPOSTA_PERGUNTA_2", respostaPergunta2) // Adiciona a resposta da P2
                startActivity(intent)
            }
        }
    }
}