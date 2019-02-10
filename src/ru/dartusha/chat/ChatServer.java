package ru.dartusha.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChatServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                //TODO проанализировать GET /somepath HTTP/1.1 - распарсить, проверить путь и если нет - выдать 404


                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) {

                    while (!input.ready()) ;

                    String ref="";
                    while (input.ready()) {
                        String s=input.readLine();
                        System.out.println(s);
                        if (s.contains("GET")){
                            ref=s.substring(4,s.indexOf("HTTP/1.1"));
                        }
                    }

                    String linkStr=System.getProperty("user.dir")+ref;
                    File f = new File(linkStr);

                    if(f.exists() || f.isDirectory()) {
                        output.println("HTTP/1.1 200 OK"); //те не 200 а 404
                        output.println("Content-Type: text/html; charset=utf-8");
                        output.println();
                        output.println("<p>Привет всем!</p>");
                        output.flush();
                    } else {
                        output.println("HTTP/1.1 404 NOT OK"); //те не 200 а 404
                        output.println("Content-Type: text/html; charset=utf-8");
                        output.println();
                        output.println("<p>Такого пути не существует :( </p>");
                        output.flush();
                    }


                }



            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
