package ru.dartusha.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                /*
                BufferedReader inputCon = new BufferedReader(new InputStreamReader(System.in));
                String inputStr= inputCon.readLine();
                System.out.println(inputStr);
                */
                //TODO проанализировать GET /somepath HTTP/1.1 - распарсить, проверить путь и если нет - выдать 404


                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) {

                    while (!input.ready()) ;

                    while (input.ready()) {
                        System.out.println(input.readLine());
                    }

                    output.println("HTTP/1.1 200 OK"); //те не 200 а 404
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();
                    output.println("<p>Привет всем!</p>");
                    output.flush();
                }



            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
