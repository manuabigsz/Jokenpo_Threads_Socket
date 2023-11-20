# Jokenpo_Threads_Socket

O Jokenpô, também conhecido como Pedra-Papel-Tesoura, é um jogo clássico onde os jogadores escolhem entre três opções: Pedra, Papel ou Tesoura, e o vencedor é determinado pelas regras do jogo.

O projeto consiste em um jogo Jokenpô multiplayer implementado em Java, utilizando comunicação cliente-servidor e Threads.

- [x] Essa aplicação gerencia o jogo entre somente dois jogadores, finalizando a conexão de todas elas caso receba mais uma solicitação de conexão.
- [x] Ele gerencia as conexões dos jogadores, armazenando na classe Jogadores, recebendo o nome, id, ip, socket, entrada, resposta e pontos.
- [x] Informa aos jogadores quando é a vez de jogar, pontuação do jogo, desistência, encerramente ou outras mensagens.
- [x] O jogo deve durar 10 jogadas, onde cada jogada vale 1 ponto.
- [x] É informado aos jogadores as informações sobre pontuação total de todos os participantes
- [x] Se houver empate, os jogadores devem ser informados e jogar outra vez até desempatar;
- [x] O jogo encerra quando um dos jogadores atingir 10 pontos ou quando um deles digitar a palavra "sair"
- [x] Ao encerrrar o jogo, cada jogador deve ser informado sobre sua pontuação e também o jogador vencedor.
