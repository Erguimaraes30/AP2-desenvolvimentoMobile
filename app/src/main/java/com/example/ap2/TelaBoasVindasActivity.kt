package com.example.ap2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.textfield.TextInputEditText

class TelaBoasVindasActivity : AppCompatActivity() {

    private lateinit var editTextNome: TextInputEditText
    private lateinit var seekBarIdade: SeekBar
    private lateinit var textViewIdadeSelecionada: TextView
    private lateinit var btnComecar: Button

    private val MIN_AGE = 1
    private val MAX_AGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_boas_vindas)


        editTextNome = findViewById(R.id.editTextNome)
        textViewIdadeSelecionada = findViewById(R.id.textViewIdadeSelecionada)
        seekBarIdade = findViewById(R.id.seekBarIdade)
        btnComecar = findViewById(R.id.btnComecar)


        seekBarIdade.max = MAX_AGE - MIN_AGE


        val initialProgress = 17
        seekBarIdade.progress = initialProgress

        textViewIdadeSelecionada.text = (seekBarIdade.progress + MIN_AGE).toString()

        seekBarIdade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val idadeAtual = progress + MIN_AGE
                textViewIdadeSelecionada.text = idadeAtual.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        btnComecar.setOnClickListener {
            val nome = editTextNome.text.toString().trim()
            val idade = seekBarIdade.progress + MIN_AGE

            if (nome.isEmpty()) {

                val nomeLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textFieldNomeLayout)
                nomeLayout.error = "Nome é obrigatório"
                editTextNome.requestFocus()
                return@setOnClickListener
            } else {
                val nomeLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textFieldNomeLayout)
                nomeLayout.error = null // Limpar erro
            }



            Log.d("TelaBoasVindas", "Nome: $nome, Idade: $idade")

            val intent = Intent(this, Pergunta1Activity::class.java)
            intent.putExtra("NOME_USUARIO", nome)
            intent.putExtra("IDADE_USUARIO", idade)
            startActivity(intent)

        }
    }
}