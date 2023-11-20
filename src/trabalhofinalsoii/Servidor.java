/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhofinalsoii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author manu
 */
public class Servidor extends Thread {

    static List<Jogador> listaClientes;
    Jogador jogador1;
    Jogador jogador2;

    public Servidor(Jogador j1, Jogador j2) {
        this.jogador1 = j1;
        this.jogador2 = j2;
    }

    public void run() {
        try {
            jogador1.getResposta().println("Bem-vindo ao Jokenpô! Aguarde o outro jogador.");

            String nomeJogador1 = jogador1.getEntrada().readLine();
            String nomeJogador2 = jogador2.getEntrada().readLine();

            jogador1.setNome(nomeJogador1);
            jogador2.setNome(nomeJogador2);

            jogador1.getResposta().println("Seu oponente é: " + jogador2.getNome());
            jogador2.getResposta().println("Seu oponente é: " + jogador1.getNome());

            int rodadas = 0;
            
            while (rodadas < 10) {
                jogador1.getResposta().println("Faca sua jogada, " + nomeJogador1);

                String moveJogador1 = jogador1.getEntrada().readLine();
                System.out.println(nomeJogador1 + " jogou: " + moveJogador1);

                jogador2.getResposta().println("Faca sua jogada, " + nomeJogador2);

                String moveJogador2 = jogador2.getEntrada().readLine();
                System.out.println(nomeJogador2 + " jogou: " + moveJogador2);

                String resultado = determinarVencedor(moveJogador1, moveJogador2, jogador1, jogador2);
                jogador1.getResposta().println(resultado);
                jogador2.getResposta().println(resultado);

                jogador1.getResposta().println("Sua pontuação: " + jogador1.getPontos());
                jogador2.getResposta().println("Sua pontuação: " + jogador2.getPontos());
                
                rodadas++;
            }
            
            if(jogador1.getPontos()<jogador2.getPontos()){
                System.out.println(jogador1.getNome() + " ganhou com " + jogador1.getPontos());
            }else{
                System.out.println(jogador2.getNome() + " ganhou com " + jogador2.getPontos());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String determinarVencedor(String moveJogador1, String moveJogador2, Jogador jogador1, Jogador jogador2) {
        Socket socketJogador2 = jogador2.getSocket();

        try {
            PrintStream saidaJogador2 = new PrintStream(socketJogador2.getOutputStream());
            saidaJogador2.println("Seu oponente jogou " + moveJogador1);

            if (moveJogador1.equals(moveJogador2)) {
                return "Empate! Jogue novamente.";
            } else if ((moveJogador1.equals("pedra") && moveJogador2.equals("tesoura"))) {
                jogador1.setPontos(jogador1.getPontos() + 1);
                return jogador1.getNome() + " venceu! " + jogador1.getNome() + " jogou pedra e " + jogador2.getNome() + " jogou tesoura.";

            } else if ((moveJogador1.equals("tesoura") && moveJogador2.equals("papel"))) {
                jogador1.setPontos(jogador1.getPontos() + 1);
                return jogador1.getNome() + " venceu! " + jogador1.getNome() + " jogou tesoura e " + jogador2.getNome() + " jogou papel.";
            } else if (moveJogador1.equals("papel") && moveJogador2.equals("pedra")) {
                jogador1.setPontos(jogador1.getPontos() + 1);
                return jogador1.getNome() + " venceu! " + jogador1.getNome() + " jogou papel e " + jogador2.getNome() + " jogou pedra.";
            } else if ((moveJogador2.equals("pedra") && moveJogador1.equals("tesoura"))) {
                jogador2.setPontos(jogador2.getPontos() + 1);
                return jogador2.getNome() + " venceu! " + jogador2.getNome() + " jogou pedra e " + jogador1.getNome() + " jogou tesoura.";
            } else if ((moveJogador2.equals("tesoura") && moveJogador1.equals("papel"))) {
                jogador2.setPontos(jogador2.getPontos() + 1);
                return jogador2.getNome() + " venceu! " + jogador2.getNome() + " jogou tesoura e " + jogador1.getNome() + " jogou papel.";
            } else if (moveJogador2.equals("papel") && moveJogador1.equals("pedra")) {
                jogador2.setPontos(jogador2.getPontos() + 1);
                return jogador2.getNome() + " venceu! " + jogador2.getNome() + " jogou papel e " + jogador1.getNome() + " jogou pedra.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao determinar o resultado.";
        }
        return null;
    }

    public static void main(String[] args) {
        listaClientes = new ArrayList<>();

        try {
            ServerSocket server = new ServerSocket(6000);

            for (int i = 0; i < 2; i++) {
                System.out.println("Esperando jogador " + (i + 1) + " se conectar...");
                Socket conexao = server.accept();
                Jogador jogador = new Jogador(conexao);
                listaClientes.add(jogador);

                System.out.println("Jogador " + (i + 1) + " se conectou.");
            }

            Thread threadJogo = new Servidor(listaClientes.get(0), listaClientes.get(1));
            threadJogo.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
