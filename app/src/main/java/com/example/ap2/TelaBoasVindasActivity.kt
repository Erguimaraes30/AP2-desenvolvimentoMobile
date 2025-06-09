package com.example.ap2

import android.content.Intent
import android.os.Bundle
import android.util.Log // Adicionado para depuração
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
// import android.widget.Toast // Não estamos mais usando Toast aqui, mas pode ser útil
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.textfield.TextInputEditText

class TelaBoasVindasActivity : AppCompatActivity() {

    private lateinit var editTextNome: TextInputEditText // Para o nome
    private lateinit var seekBarIdade: SeekBar
    private lateinit var textViewIdadeSelecionada: TextView
    private lateinit var btnComecar: Button

    private val MIN_AGE = 1
    private val MAX_AGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_boas_vindas)

        // Referências das Views do XML
        editTextNome = findViewById(R.id.editTextNome)
        textViewIdadeSelecionada = findViewById(R.id.textViewIdadeSelecionada) // Para mostrar a idade
        seekBarIdade = findViewById(R.id.seekBarIdade) // O SeekBar
        btnComecar = findViewById(R.id.btnComecar)

        // Configurar o SeekBar
        // O valor do seekBar.progress vai de 0 a (MAX_AGE - MIN_AGE)
        // O valor exibido e usado será seekBar.progress + MIN_AGE
        seekBarIdade.max = MAX_AGE - MIN_AGE // Para que o progress vá de 0 a 99 (100 - 1)

        // Definir um valor inicial para o SeekBar (ex: para começar com 18 anos, progress = 17)
        val initialProgress = 17 // Idade inicial de 18 (17 + MIN_AGE)
        seekBarIdade.progress = initialProgress

        // Atualizar o TextView com o valor inicial do SeekBar
        textViewIdadeSelecionada.text = (seekBarIdade.progress + MIN_AGE).toString()

        seekBarIdade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // progress aqui vai de 0 a (MAX_AGE - MIN_AGE)
                val idadeAtual = progress + MIN_AGE
                textViewIdadeSelecionada.text = idadeAtual.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Opcional: Ação quando o usuário começa a arrastar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Opcional: Ação quando o usuário para de arrastar
            }
        })

        btnComecar.setOnClickListener {
            val nome = editTextNome.text.toString().trim()
            val idade = seekBarIdade.progress + MIN_AGE // Obter idade do SeekBar

            if (nome.isEmpty()) {
                // Usar o TextInputLayout para mostrar o erro é melhor se ele existir
                val nomeLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textFieldNomeLayout)
                nomeLayout.error = "Nome é obrigatório"
                editTextNome.requestFocus() // Ainda foca no TextInputEditText
                return@setOnClickListener
            } else {
                val nomeLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.textFieldNomeLayout)
                nomeLayout.error = null // Limpar erro
            }

            // A validação de idade (vazio, formato, range) não é mais necessária como antes,
            // pois o SeekBar já garante um valor numérico dentro do range configurado.

            Log.d("TelaBoasVindas", "Nome: $nome, Idade: $idade") // Log para depuração

            val intent = Intent(this, Pergunta1Activity::class.java)
            intent.putExtra("NOME_USUARIO", nome)
            intent.putExtra("IDADE_USUARIO", idade) // Passa o Int diretamente
            startActivity(intent)
            // finish() // Descomente se quiser fechar esta activity
        }
    }
}