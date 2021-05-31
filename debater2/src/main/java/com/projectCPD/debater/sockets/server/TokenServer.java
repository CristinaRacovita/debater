package com.projectCPD.debater.sockets.server;

import com.projectCPD.debater.sockets.Token;
import com.projectCPD.debater.sockets.TokenState;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


public class TokenServer extends Thread {
    private Socket socket;
    private BufferedReader bufferedReader;

    public TokenServer(int serverPort) {
        try {
            System.out.println("Server opened...");
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Waiting for someone to connect...");
            this.socket = serverSocket.accept();

            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (Exception e) {
            System.out.print("Receiving exception appeared: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            Token.value = bufferedReader.readLine();
            while (Token.value != null) {
                if (Token.value.equals(TokenState.STOP.label)) {
                    System.out.println("Stopping...");
                    stopConnection();
                    break;
                } else if (Token.value.equals(TokenState.DEBATE.label)) {
                    System.out.println("Debate");
                    if (Token.turnNumber % 3 == 1) {
                        System.out.println("I can debate now...token:" + Token.value);
                        TimeUnit.SECONDS.sleep(10);
                    } else {
                        Token.value = TokenState.PENDING.label;
                    }

                    Token.turnNumber++;

                    if (Token.turnNumber % 3 == 2) {
                        System.out.println("Finish to debate...token:" + Token.value);
                        Token.value = TokenState.SEND.label;
                    }

                    Token.turnNumber+=2;

                }
                Token.value = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.print("Receiving exception appeared: " + e.getMessage());
        }
    }

    private void stopConnection() {
        try {
            bufferedReader.close();
            socket.close();
        } catch (Exception e) {
            System.out.print("Closing exception appeared: " + e.getMessage());
        }
    }
}
