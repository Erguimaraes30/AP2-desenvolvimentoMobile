package com.example.ap2 // Substitua pelo seu nome de pacote

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ResultadoFragment : Fragment() {

    private var nomeUsuario: String? = null
    private var respostaP1: String? = null
    private var respostaP2: String? = null
    private var respostaP3: String? = null
    private var respostaP4: String? = null

    // --- NOSSOS ANIMAIS ---
    private val LOBO = "LOBO"
    private val GATO = "GATO"
    private val LEAO = "LEÃO"
    private val CACHORRO = "CACHORRO"
    private val CORUJA = "CORUJA"
    private val AGUIA = "ÁGUIA"
    private val URSO = "URSO"
    private val RAPOSA = "RAPOSA"
    private val CERVO = "CERVO"
    private val GOLFINHO = "GOLFINHO"
    private val TARTARUGA = "TARTARUGA"
    private val ESPIRITO_LIVRE = "ESPÍRITO LIVRE"
    // Adicione mais constantes de animais se precisar

    companion object {
        const val NOME_USUARIO_KEY_ARG = "NOME_USUARIO"
        const val RESPOSTA_P1_KEY_ARG = "RESPOSTA_PERGUNTA_1"
        const val RESPOSTA_P2_KEY_ARG = "RESPOSTA_PERGUNTA_2"
        const val RESPOSTA_P3_KEY_ARG = "RESPOSTA_PERGUNTA_3"
        const val RESPOSTA_P4_KEY_ARG = "RESPOSTA_PERGUNTA_4"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nomeUsuario = it.getString(NOME_USUARIO_KEY_ARG)
            respostaP1 = it.getString(RESPOSTA_P1_KEY_ARG)
            respostaP2 = it.getString(RESPOSTA_P2_KEY_ARG)
            respostaP3 = it.getString(RESPOSTA_P3_KEY_ARG)
            respostaP4 = it.getString(RESPOSTA_P4_KEY_ARG)
        }
        Log.i("ResultadoFragment", "Dados recebidos: Nome=$nomeUsuario, P1=$respostaP1, P2=$respostaP2, P3=$respostaP3, P4=$respostaP4")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_resultado, container, false)

        val textViewNomeUsuario = view.findViewById<TextView>(R.id.textViewNomeUsuarioResultado)
        val textViewAnimalResultado = view.findViewById<TextView>(R.id.textViewAnimalResultado)
        val textViewDescricaoAnimal = view.findViewById<TextView>(R.id.textViewDescricaoAnimal)
        val btnRefazerQuiz = view.findViewById<Button>(R.id.btnRefazerQuiz)
        val btnSaibaMais = view.findViewById<Button>(R.id.btnSaibaMais)
        val imageViewAnimal = view.findViewById<ImageView>(R.id.imageViewAnimal)

        textViewNomeUsuario.text = "Olá, ${nomeUsuario ?: "Aventureiro(a)"}!"

        val (animal, descricao) = determinarAnimal()
        val imagemResource = obterImagemResourceParaAnimal(animal)

        textViewAnimalResultado.text = animal
        textViewDescricaoAnimal.text = descricao

        if (imagemResource != 0) {
            imageViewAnimal.setImageResource(imagemResource)
        } else {
            // Tente usar um placeholder específico para ESPIRITO_LIVRE se não tiver um para o animal
            if (animal == ESPIRITO_LIVRE) {
                imageViewAnimal.setImageResource(R.drawable.espirito_livre_placeholder)
            } else {
                imageViewAnimal.setImageResource(R.drawable.placeholder_animal) // Imagem placeholder genérica
            }
        }

        btnRefazerQuiz.setOnClickListener {
            val intent: Intent? = if (activity?.packageName != null) {
                activity?.packageManager?.getLaunchIntentForPackage(requireActivity().packageName)?.apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                } ?: run {
                    // Fallback se você não tiver uma TelaBoasVindasActivity ou MainActivity como launcher
                    // e quiser ir direto para a Pergunta1Activity.
                    // Ajuste 'com.example.ap2.Pergunta1Activity' para o nome completo da sua Pergunta1Activity.
                    // Intent(activity, Class.forName("com.example.ap2.Pergunta1Activity")).apply {
                    //    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    // }
                    null // Deixe nulo se o getLaunchIntentForPackage for a melhor abordagem geral
                }
            } else {
                null
            }

            if (intent != null) {
                try {
                    startActivity(intent)
                    activity?.finishAffinity()
                } catch (e: Exception) {
                    Toast.makeText(context, "Não foi possível reiniciar o quiz.", Toast.LENGTH_SHORT).show()
                    Log.e("ResultadoFragment", "Erro ao tentar reiniciar o quiz", e)
                }
            } else {
                Toast.makeText(context, "Não foi possível reiniciar o quiz (intent nulo).", Toast.LENGTH_SHORT).show()
                Log.e("ResultadoFragment", "Intent para reiniciar o quiz é nulo.")
            }
        }

        btnSaibaMais.setOnClickListener {
            val animalNome = textViewAnimalResultado.text.toString()
            val url = obterUrlParaAnimal(animalNome)

            if (url.isNotEmpty()) {
                try {
                    val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intentWeb)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Nenhum aplicativo encontrado para abrir links da web.", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Não foi possível abrir o link.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Link não disponível para este resultado.", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun obterUrlParaAnimal(animalNome: String): String {
        val baseUrl = "https://pt.wikipedia.org/wiki/"
        return when (animalNome.uppercase()) {
            LOBO -> baseUrl + "Lobo"
            GATO -> baseUrl + "Gato"
            LEAO -> baseUrl + "Leão"
            CACHORRO -> baseUrl + "Cão" // Wikipedia usa "Cão"
            CORUJA -> baseUrl + "Coruja"
            AGUIA -> baseUrl + "Águia"
            URSO -> baseUrl + "Urso"
            RAPOSA -> baseUrl + "Raposa"
            CERVO -> baseUrl + "Cervo"
            GOLFINHO -> baseUrl + "Golfinho"
            TARTARUGA -> baseUrl + "Tartaruga"
            ESPIRITO_LIVRE -> "https://www.google.com/search?q=espírito+livre+significado"
            else -> ""
        }
    }

    private fun obterImagemResourceParaAnimal(animalNome: String): Int {
        // Certifique-se que você tem esses drawables no seu projeto (ex: res/drawable/lobo.png)
        return when (animalNome) {
            LOBO -> R.drawable.lobo
            GATO -> R.drawable.gato
            LEAO -> R.drawable.leao
            CACHORRO -> R.drawable.cachorro // crie R.drawable.cachorro
            CORUJA -> R.drawable.coruja     // crie R.drawable.coruja
            AGUIA -> R.drawable.aguia      // crie R.drawable.aguia
            URSO -> R.drawable.urso        // crie R.drawable.urso
            RAPOSA -> R.drawable.raposa    // crie R.drawable.raposa
            CERVO -> R.drawable.cervo      // crie R.drawable.cervo
            GOLFINHO -> R.drawable.golfinho// crie R.drawable.golfinho
            TARTARUGA -> R.drawable.tartaruga// crie R.drawable.tartaruga
            ESPIRITO_LIVRE -> R.drawable.espirito_livre_placeholder // Mantenha ou crie um placeholder
            else -> R.drawable.placeholder_animal // Um placeholder genérico
        }
    }

    private fun determinarAnimal(): Pair<String, String> {
        var pontuacaoLobo = 0
        var pontuacaoGato = 0
        var pontuacaoLeao = 0
        var pontuacaoCachorro = 0
        var pontuacaoCoruja = 0
        var pontuacaoAguia = 0
        var pontuacaoUrso = 0
        var pontuacaoRaposa = 0
        var pontuacaoCervo = 0
        var pontuacaoGolfinho = 0
        var pontuacaoTartaruga = 0

        Log.d("ResultadoFragment", "Respostas para determinar animal: P1='${respostaP1}', P2='${respostaP2}', P3='${respostaP3}', P4='${respostaP4}'")

        // --- PONTUAÇÃO DAS PERGUNTAS ---
        // Ajuste as strings e os valores de pontuação conforme as opções do seu quiz!

        // Pergunta 1: Onde você se sente mais você mesmo? (Exemplo de opções)
        // Opção 1: "Explorando a natureza selvagem"
        // Opção 2: "Em casa, relaxando com um bom livro ou filme"
        // Opção 3: "Socializando com amigos e família"
        // Opção 4: "Aprendendo algo novo ou resolvendo um quebra-cabeça"
        when {
            respostaP1?.contains("natureza", ignoreCase = true) == true -> { // "Explorando a natureza"
                pontuacaoLobo += 2; pontuacaoAguia += 2; pontuacaoUrso += 2; pontuacaoCervo += 1; pontuacaoRaposa +=1
            }
            respostaP1?.contains("casa", ignoreCase = true) == true || respostaP1?.contains("livro", ignoreCase = true) == true -> { // "Em casa, relaxando"
                pontuacaoGato += 2; pontuacaoTartaruga += 2; pontuacaoCoruja += 1
            }
            respostaP1?.contains("socializando", ignoreCase = true) == true || respostaP1?.contains("amigos", ignoreCase = true) == true -> { // "Socializando"
                pontuacaoCachorro += 2; pontuacaoGolfinho += 2; pontuacaoLeao += 1; pontuacaoCervo +=1
            }
            respostaP1?.contains("aprendendo", ignoreCase = true) == true || respostaP1?.contains("quebra-cabeça", ignoreCase = true) == true -> { // "Aprendendo"
                pontuacaoRaposa += 2; pontuacaoCoruja += 2; pontuacaoGolfinho +=1; pontuacaoGato +=1
            }
        }

        // Pergunta 2: Como você lida com desafios inesperados? (Exemplo de opções)
        // Opção 1: "Enfrento de frente, com coragem e determinação"
        // Opção 2: "Analiso a situação cuidadosamente antes de agir"
        // Opção 3: "Peço ajuda ou conselho a amigos ou mentores"
        // Opção 4: "Busco uma solução criativa ou fora do comum"
        when {
            respostaP2?.contains("enfrenta de frente", ignoreCase = true) == true || respostaP2?.contains("coragem", ignoreCase = true) == true -> {
                pontuacaoLeao += 3; pontuacaoUrso +=2; pontuacaoLobo += 1; pontuacaoAguia +=1; pontuacaoCachorro +=1
            }
            respostaP2?.contains("analisa", ignoreCase = true) == true || respostaP2?.contains("cuidadosamente", ignoreCase = true) == true -> {
                pontuacaoCoruja += 3; pontuacaoRaposa += 2; pontuacaoGato +=1; pontuacaoTartaruga +=1
            }
            respostaP2?.contains("pede ajuda", ignoreCase = true) == true || respostaP2?.contains("conselho", ignoreCase = true) == true -> {
                pontuacaoCachorro +=2; pontuacaoGolfinho +=2; pontuacaoCervo +=1; pontuacaoLeao +=1; pontuacaoTartaruga +=1
            }
            respostaP2?.contains("criativa", ignoreCase = true) == true || respostaP2?.contains("fora do comum", ignoreCase = true) == true -> {
                pontuacaoRaposa +=3; pontuacaoGolfinho +=1; pontuacaoGato +=1; pontuacaoLobo +=1; pontuacaoAguia +=1
            }
        }

        // Pergunta 3: Qual destes lugares você escolheria para uma aventura? (Exemplo de opções)
        // Opção 1: "Uma floresta densa e misteriosa"
        // Opção 2: "Altas montanhas com vistas panorâmicas"
        // Opção 3: "Uma praia tropical com águas cristalinas"
        // Opção 4: "Uma biblioteca antiga cheia de segredos e conhecimento"
        // Opção 5: "Um lar aconchegante cercado por quem amo" (se tiver essa opção)
        when {
            respostaP3?.contains("floresta", ignoreCase = true) == true -> {
                pontuacaoLobo += 3; pontuacaoUrso +=2; pontuacaoRaposa +=2; pontuacaoCoruja +=1; pontuacaoCervo +=1
            }
            respostaP3?.contains("montanhas", ignoreCase = true) == true -> {
                pontuacaoAguia += 3; pontuacaoLobo += 2; pontuacaoUrso +=1; pontuacaoCervo +=1; pontuacaoLeao +=1
            }
            respostaP3?.contains("praia", ignoreCase = true) == true -> {
                pontuacaoGolfinho +=3; pontuacaoTartaruga +=2; pontuacaoGato +=1; pontuacaoAguia +=1 // Águias pescam
            }
            respostaP3?.contains("biblioteca", ignoreCase = true) == true -> {
                pontuacaoCoruja +=3; pontuacaoRaposa +=1; pontuacaoGato+=1; pontuacaoTartaruga +=1
            }
            respostaP3?.contains("lar aconchegante", ignoreCase = true) == true -> { // Se tiver essa opção
                pontuacaoGato +=2; pontuacaoCachorro +=2; pontuacaoTartaruga+=1; pontuacaoUrso+=1; pontuacaoCervo +=1
            }
        }

        // Pergunta 4: Qual destas qualidades mais te define? (Suas opções de RadioButton)
        // "Lealdade"
        // "Independência"
        // "Curiosidade"
        // "Calma e paciência"
        // "Liderança e Coragem" (exemplo de adição)
        // "Sabedoria e Intuição" (exemplo de adição)
        when {
            respostaP4?.contains("Lealdade", ignoreCase = true) == true -> {
                pontuacaoCachorro += 3; pontuacaoLobo += 2; pontuacaoLeao += 1; pontuacaoGolfinho +=1; pontuacaoCervo +=1
            }
            respostaP4?.contains("Independência", ignoreCase = true) == true -> {
                pontuacaoGato += 3; pontuacaoRaposa += 2; pontuacaoAguia +=2; pontuacaoCoruja +=1; pontuacaoLobo +=1; pontuacaoUrso +=1
            }
            respostaP4?.contains("Curiosidade", ignoreCase = true) == true -> {
                pontuacaoRaposa += 3; pontuacaoGato += 2; pontuacaoGolfinho +=2; pontuacaoCoruja +=1; pontuacaoCachorro+=1
            }
            respostaP4?.contains("Calma", ignoreCase = true) == true || respostaP4?.contains("paciência", ignoreCase = true) == true -> {
                pontuacaoTartaruga += 3; pontuacaoCervo +=2; pontuacaoCoruja +=1; pontuacaoUrso +=1; pontuacaoGato +=1
            }
            // Exemplos de mais opções que você poderia ter na P4:
            respostaP4?.contains("Liderança", ignoreCase = true) == true || respostaP4?.contains("Coragem", ignoreCase = true) == true -> {
                pontuacaoLeao += 3; pontuacaoAguia += 2; pontuacaoUrso += 1; pontuacaoLobo +=1; pontuacaoCachorro +=1
            }
            respostaP4?.contains("Sabedoria", ignoreCase = true) == true || respostaP4?.contains("Intuição", ignoreCase = true) == true -> {
                pontuacaoCoruja += 3; pontuacaoTartaruga += 2; pontuacaoRaposa += 1; pontuacaoGolfinho +=1; pontuacaoCervo +=1
            }
        }

        val todasAsPontuacoes = mutableMapOf(
            LOBO to pontuacaoLobo, GATO to pontuacaoGato, LEAO to pontuacaoLeao,
            CACHORRO to pontuacaoCachorro, CORUJA to pontuacaoCoruja, AGUIA to pontuacaoAguia,
            URSO to pontuacaoUrso, RAPOSA to pontuacaoRaposa, CERVO to pontuacaoCervo,
            GOLFINHO to pontuacaoGolfinho, TARTARUGA to pontuacaoTartaruga
        )

        Log.i("ResultadoFragment", "Pontuações Calculadas: $todasAsPontuacoes")

        val LIMIAR_MINIMO_PONTUACAO_ANIMAL = 4 // Ajuste: Com mais animais/pontos, pode aumentar o limiar. Teste!

        val pontuacoesAltas = todasAsPontuacoes.filterValues { it >= LIMIAR_MINIMO_PONTUACAO_ANIMAL }

        if (pontuacoesAltas.isEmpty()) {
            val algumaPontuacao = todasAsPontuacoes.values.any { it > 0 }
            if (algumaPontuacao) {
                val maxPontuacaoBaixa = todasAsPontuacoes.maxByOrNull { it.value }?.value ?: 0
                if (maxPontuacaoBaixa > 0) {
                    val animaisComMaxPontuacaoBaixa = todasAsPontuacoes.filterValues { it == maxPontuacaoBaixa }
                    // Desempate para pontuação baixa: pode ser aleatório ou o primeiro
                    val animalEscolhido = animaisComMaxPontuacaoBaixa.keys.shuffled().firstOrNull() ?: animaisComMaxPontuacaoBaixa.keys.first()

                    Log.i("ResultadoFragment", "Nenhum animal atingiu o limiar ($LIMIAR_MINIMO_PONTUACAO_ANIMAL), mas houve pontuação. Escolhido o de maior pontuação baixa: $animalEscolhido ($maxPontuacaoBaixa)")
                    return when (animalEscolhido) {
                        LOBO -> Pair(LOBO, "Há um forte instinto e um espírito livre latente em você, buscando conexões verdadeiras.")
                        GATO -> Pair(GATO, "Sua independência e charme se destacam, mesmo que você aprecie um momento de tranquilidade.")
                        LEAO -> Pair(LEAO, "Uma centelha de coragem e uma presença naturalmente nobre brilham em seu espírito.")
                        CACHORRO -> Pair(CACHORRO, "Sua lealdade e um coração amigável são qualidades notáveis que aquecem quem está por perto.")
                        CORUJA -> Pair(CORUJA, "Uma sabedoria tranquila e uma capacidade de observação aguçada guiam seus passos.")
                        AGUIA -> Pair(AGUIA, "Você tem uma visão para o futuro e um desejo inato de alcançar grandes alturas, mesmo que discretamente.")
                        URSO -> Pair(URSO, "Sua força interior e uma natureza protetora são admiráveis, oferecendo segurança e conforto.")
                        RAPOSA -> Pair(RAPOSA, "Sua astúcia e notável capacidade de adaptação te ajudam a encontrar caminhos inteligentes.")
                        CERVO -> Pair(CERVO, "Uma gentileza e graça naturais emanam de sua personalidade, trazendo calma ao seu redor.")
                        GOLFINHO -> Pair(GOLFINHO, "Sua natureza sociável e uma inteligência brilhante te conectam facilmente e com alegria.")
                        TARTARUGA -> Pair(TARTARUGA, "Com paciência e uma resiliência admirável, você constrói seu caminho com sabedoria e constância.")
                        else -> Pair(ESPIRITO_LIVRE, "Sua alma é uma tapeçaria rica e indomável, difícil de definir por um único espírito animal!")
                    }
                } else {
                    Log.w("ResultadoFragment", "Nenhum animal pontuou (todos 0). Retornando Espírito Livre.")
                    return Pair(ESPIRITO_LIVRE, "Sua alma é indomável e multifacetada, uma combinação única de qualidades que transcende uma única forma animal!")
                }
            } else {
                Log.w("ResultadoFragment", "Nenhum animal pontuou. Retornando Espírito Livre.")
                return Pair(ESPIRITO_LIVRE, "Sua alma é indomável e multifacetada, uma combinação única de qualidades que transcende uma única forma animal!")
            }
        }

        val maxPontuacao = pontuacoesAltas.maxByOrNull { it.value }?.value ?: 0
        val animaisComMaxPontuacao = pontuacoesAltas.filterValues { it == maxPontuacao }

        val animalEscolhido: String

        if (animaisComMaxPontuacao.size > 1) {
            Log.i("ResultadoFragment", "Empate detectado entre ${animaisComMaxPontuacao.keys} com $maxPontuacao pontos (acima do limiar).")
            // Desempate aleatório para mais justiça entre os empatados
            animalEscolhido = animaisComMaxPontuacao.keys.shuffled().first()
            Log.i("ResultadoFragment", "Desempate (acima do limiar): Escolhendo aleatoriamente: $animalEscolhido")
        } else {
            animalEscolhido = animaisComMaxPontuacao.keys.first()
            Log.i("ResultadoFragment", "Animal Escolhido (acima do limiar): $animalEscolhido com $maxPontuacao pontos.")
        }

        return when (animalEscolhido) {
            LOBO -> Pair(LOBO, "Você é o Lobo! Instintivo, leal à sua matilha e com um amor profundo pela liberdade da natureza selvagem. Você valoriza os laços fortes e a autenticidade.")
            GATO -> Pair(GATO, "Você é o Gato! Independente, charmoso e aprecia o conforto, mas sempre com uma curiosidade aguçada para a aventura. Seu mistério é cativante.")
            LEAO -> Pair(LEAO, "Você é o Leão! Corajoso, líder nato e carismático. Sua presença magnética e determinação inspiram confiança e respeito nos outros.")
            CACHORRO -> Pair(CACHORRO, "Você é o Cachorro! Leal, amigável e cheio de uma energia contagiante. Você adora companhia, proteger quem ama e espalhar alegria.")
            CORUJA -> Pair(CORUJA, "Você é a Coruja! Sábia, observadora e prefere a tranquilidade para refletir. Sua visão aguçada e intuição te guiam através dos mistérios da vida.")
            AGUIA -> Pair(AGUIA, "Você é a Águia! Com uma visão ampla e foco aguçado, você busca a liberdade e almeja grandes alturas. Sua nobreza e independência são inspiradoras.")
            URSO -> Pair(URSO, "Você é o Urso! Forte, protetor e profundamente conectado com os ciclos da natureza. Você valoriza a introspecção, a força tranquila e o conforto do seu lar.")
            RAPOSA -> Pair(RAPOSA, "Você é a Raposa! Astuta, adaptável e incrivelmente inteligente. Você encontra soluções criativas para os desafios e navega pela vida com sagacidade.")
            CERVO -> Pair(CERVO, "Você é o Cervo! Gentil, gracioso e intuitivo. Você se move pela vida com sensibilidade, uma calma nobre e uma forte conexão com o mundo ao seu redor.")
            GOLFINHO -> Pair(GOLFINHO, "Você é o Golfinho! Sociável, inteligente e brincalhão. Você navega pela vida com alegria, comunicação clara e uma habilidade natural para criar laços.")
            TARTARUGA -> Pair(TARTARUGA, "Você é a Tartaruga! Sábia, paciente e resiliente. Você segue seu próprio ritmo, construindo uma base sólida e enfrentando a vida com calma e determinação.")
            else -> Pair(ESPIRITO_LIVRE, "Sua alma é uma tapeçaria rica e indomável, transcendendo uma única forma animal. Você é uma combinação única de qualidades!")
        }
    }
}