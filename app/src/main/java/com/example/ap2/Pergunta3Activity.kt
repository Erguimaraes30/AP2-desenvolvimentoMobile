package com.example.ap2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Pergunta3Activity : AppCompatActivity() {

    private lateinit var textViewPergunta: TextView
    private lateinit var radioGroupOpcoes: RadioGroup
    private lateinit var btnProxima: Button


    private var nomeUsuario: String? = null
    private var idadeUsuario: Int = -1
    private var respostaPergunta1: String? = null
    private var respostaPergunta2: String? = null


    private var respostaPergunta3: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergunta3)


        nomeUsuario = intent.getStringExtra("NOME_USUARIO")
        idadeUsuario = intent.getIntExtra("IDADE_USUARIO", -1)
        respostaPergunta1 = intent.getStringExtra("RESPOSTA_PERGUNTA_1")
        respostaPergunta2 = intent.getStringExtra("RESPOSTA_PERGUNTA_2")



        textViewPergunta = findViewById(R.id.textViewPergunta3)
        radioGroupOpcoes = findViewById(R.id.radioGroupPergunta3)
        btnProxima = findViewById(R.id.btnProximaPergunta3)

        btnProxima.setOnClickListener {
            val selectedOptionId = radioGroupOpcoes.checkedRadioButtonId
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Por favor, selecione uma opção.", Toast.LENGTH_SHORT).show()
            } else {
                val radioButtonSelecionado = findViewById<RadioButton>(selectedOptionId)
                respostaPergunta3 = radioButtonSelecionado.text.toString()


                val intent = Intent(this, Pergunta4Activity::class.java)
                intent.putExtra("NOME_USUARIO", nomeUsuario)
                intent.putExtra("IDADE_USUARIO", idadeUsuario)
                intent.putExtra("RESPOSTA_PERGUNTA_1", respostaPergunta1)
                intent.putExtra("RESPOSTA_PERGUNTA_2", respostaPergunta2)
                intent.putExtra("RESPOSTA_PERGUNTA_3", respostaPergunta3)
                startActivity(intent)
            }
        }
    }
}