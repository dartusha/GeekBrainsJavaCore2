package ru.dartusha.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class EchoServer {


    public static void main(String[] args) {

        List<Socket> sockets = new ArrayList<Socket>();
        //массив потоков, чтобы на все клиенты передалось сообщение от сервера

        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            System.out.println("Server started!");
            BufferedReader inputCon = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                System.out.println("Client connected!");

                Thread io=
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                while (true) {
                                    String inputStr = null;
                                    try {
                                        inputStr = "Сообщение от сервера: "+inputCon.readLine();
                                        for (Socket i:sockets) {
                                            DataOutputStream out = null;
                                                try {
                                                    out = new DataOutputStream(i.getOutputStream());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                            out.writeUTF(inputStr);
                                            out.flush();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println(inputStr);
                                }
                            }
                        });
                io.start();


                Thread oi=
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        try (DataInputStream inp = new DataInputStream(socket.getInputStream());
                             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

                            while (true) {
                                String msg = inp.readUTF();
                                System.out.println("Сообщение от клиента: " + msg);
                                out.writeUTF(msg);
                                out.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                oi.start();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}