package com.example.ap2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Pergunta1Activity : AppCompatActivity() {

    private lateinit var textViewPergunta: TextView
    private lateinit var radioGroupOpcoes: RadioGroup
    private lateinit var btnProxima: Button


    private var nomeUsuario: String? = null
    private var idadeUsuario: Int = -1


    private var respostaPergunta1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergunta1)


        nomeUsuario = intent.getStringExtra("NOME_USUARIO")
        idadeUsuario = intent.getIntExtra("IDADE_USUARIO", -1)


        if (!nomeUsuario.isNullOrEmpty()) {

        }

        textViewPergunta = findViewById(R.id.textViewPergunta1)
        radioGroupOpcoes = findViewById(R.id.radioGroupPergunta1)
        btnProxima = findViewById(R.id.btnProximaPergunta1)



        btnProxima.setOnClickListener {
            val selectedOptionId = radioGroupOpcoes.checkedRadioButtonId
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Por favor, selecione uma opção.", Toast.LENGTH_SHORT).show()
            } else {
                val radioButtonSelecionado = findViewById<RadioButton>(selectedOptionId)
                respostaPergunta1 = radioButtonSelecionado.text.toString()


                val intent = Intent(this, Pergunta2Activity::class.java)
                intent.putExtra("NOME_USUARIO", nomeUsuario)
                intent.putExtra("IDADE_USUARIO", idadeUsuario)
                intent.putExtra("RESPOSTA_PERGUNTA_1", respostaPergunta1)
                startActivity(intent)
            }
        }
    }
}