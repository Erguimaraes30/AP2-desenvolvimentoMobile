package com.example.ap2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Pergunta4Activity : AppCompatActivity() {

    private lateinit var radioGroupPergunta4: RadioGroup
    private lateinit var btnProximoP4: Button


    private lateinit var contentLayoutPerguntasP4: LinearLayout
    private lateinit var fragmentContainerWrapper: FrameLayout


    private var nomeUsuario: String? = null
    private var respostaPergunta1: String? = null
    private var respostaPergunta2: String? = null
    private var respostaPergunta3: String? = null


    private var respostaPergunta4: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pergunta4)

        Log.d("Pergunta4Activity", "onCreate: Iniciando Pergunta 4")


        nomeUsuario = intent.getStringExtra("NOME_USUARIO")
        respostaPergunta1 = intent.getStringExtra("RESPOSTA_PERGUNTA_1")
        respostaPergunta2 = intent.getStringExtra("RESPOSTA_PERGUNTA_2")
        respostaPergunta3 = intent.getStringExtra("RESPOSTA_PERGUNTA_3")

        Log.i("Pergunta4Activity", "Dados Recebidos: Nome=$nomeUsuario, P1=$respostaPergunta1, P2=$respostaPergunta2, P3=$respostaPergunta3")


        contentLayoutPerguntasP4 = findViewById(R.id.contentLayoutPerguntasP4)
        fragmentContainerWrapper = findViewById(R.id.fragment_container_resultado_p4_wrapper)


        radioGroupPergunta4 = contentLayoutPerguntasP4.findViewById(R.id.radioGroupPergunta4)
        btnProximoP4 = contentLayoutPerguntasP4.findViewById(R.id.btnProximoP4)


        btnProximoP4.setOnClickListener {
            val selectedOptionId = radioGroupPergunta4.checkedRadioButtonId
            if (selectedOptionId == -1) {
                Toast.makeText(this, "Por favor, selecione uma qualidade.", Toast.LENGTH_SHORT).show()
            } else {
                val radioButtonSelecionado = findViewById<RadioButton>(selectedOptionId)
                respostaPergunta4 = radioButtonSelecionado.text.toString()
                Log.i("Pergunta4Activity", "Resposta P4 selecionada: $respostaPergunta4")

                mostrarResultado()
            }
        }

        // Visibilidade inicial
        contentLayoutPerguntasP4.visibility = View.VISIBLE
        fragmentContainerWrapper.visibility = View.GONE
    }

    private fun mostrarResultado() {
        Log.i("Pergunta4Activity", "mostrarResultado: Preparando para exibir o ResultadoFragment.")


        contentLayoutPerguntasP4.visibility = View.GONE


        fragmentContainerWrapper.visibility = View.VISIBLE

        val resultadoFragment = ResultadoFragment().apply {
            arguments = Bundle().apply {
                putString("NOME_USUARIO", nomeUsuario)
                putString("RESPOSTA_PERGUNTA_1", respostaPergunta1)
                putString("RESPOSTA_PERGUNTA_2", respostaPergunta2)
                putString("RESPOSTA_PERGUNTA_3", respostaPergunta3)
                putString("RESPOSTA_PERGUNTA_4", respostaPergunta4)
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_resultado_p4, resultadoFragment)
            .commit()
    }
}