/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalhofinalsoii;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author manu
 */
public class JogadorThread extends Thread{
    
     static boolean done = false;
    Socket conexao;
    String playerName;

    public JogadorThread(Socket s, String playerName) {
        this.conexao = s;
        this.playerName = playerName;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            PrintStream saida = new PrintStream(conexao.getOutputStream());

            String welcomeMessage = entrada.readLine();
            System.out.println(welcomeMessage);

            while (true) {
                String message = entrada.readLine();
                System.out.println(message);

                if (message.contains("Faca sua jogada")) {
                    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print(">> ");
                    String userMove = userInput.readLine();

                    saida.println(userMove);
                } else if (message.contains("Jogo encerrado")) {
                    done = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Socket conexao = new Socket("127.0.0.1", 6000);
            PrintStream saida = new PrintStream(conexao.getOutputStream());

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Digite seu nome: ");
            String playerName = userInput.readLine();

            
            saida.println(playerName);

            Thread t = new JogadorThread(conexao, playerName);
            t.start();

            while (true) {
                if (done) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
