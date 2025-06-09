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


    private var nomeUsuario: String? = null
    private var idadeUsuario: Int = -1
    private var respostaPergunta1: String? = null


    private var respostaPergunta2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergunta2)


        nomeUsuario = intent.getStringExtra("NOME_USUARIO")
        idadeUsuario = intent.getIntExtra("IDADE_USUARIO", -1)
        respostaPergunta1 = intent.getStringExtra("RESPOSTA_PERGUNTA_1")



        textViewPergunta = findViewById(R.id.textViewPergunta2)
        radioGroupOpcoes = findViewById(R.id.radioGroupPergunta2)
        btnProxima = findViewById(R.id.btnProximaPergunta2)



        btnProxima.setOnClickListener {
            val selectedOptionId = radioGroupOpcoes.checkedRadioButtonId
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Por favor, selecione uma opção.", Toast.LENGTH_SHORT).show()
            } else {
                val radioButtonSelecionado = findViewById<RadioButton>(selectedOptionId)
                respostaPergunta2 = radioButtonSelecionado.text.toString()


                val intent = Intent(this, Pergunta3Activity::class.java)
                intent.putExtra("NOME_USUARIO", nomeUsuario)
                intent.putExtra("IDADE_USUARIO", idadeUsuario)
                intent.putExtra("RESPOSTA_PERGUNTA_1", respostaPergunta1)
                intent.putExtra("RESPOSTA_PERGUNTA_2", respostaPergunta2)
                startActivity(intent)
            }
        }
    }
}