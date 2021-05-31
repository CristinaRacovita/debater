package com.projectCPD.debater.sockets.client;

import com.projectCPD.debater.sockets.Token;
import com.projectCPD.debater.sockets.TokenState;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class TokenClient extends Thread {
    private Socket clientSocket;
    private PrintWriter printWriter;

    public TokenClient(int clientPort, String clientIP) {
        try {
            this.clientSocket = new Socket(clientIP, clientPort);
            System.out.println("Client connected...");
            this.printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.print("Connection exception appeared: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Token.value.equals(TokenState.SEND.label)) {
                    printWriter.println(TokenState.DEBATE.label);
                    Token.value = TokenState.PENDING.label;
                    TimeUnit.SECONDS.sleep(1);

                } else if (Token.value.equals(TokenState.STOP.label)) {
                    System.out.println("Stopping...");
                    printWriter.println(Token.value);
                    closeClient();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Sending error appears. " + e.getMessage());
        }
    }

    private void closeClient() throws IOException {
        clientSocket.close();
        printWriter.close();
    }
}
