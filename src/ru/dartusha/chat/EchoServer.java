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
        List<Thread> threadsIO = new ArrayList<Thread>();
        List<Thread> threadsOI = new ArrayList<Thread>();
        //массив потоков, чтобы на все клиенты передалось сообщение от сервера

        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            System.out.println("Server started!");
            BufferedReader inputCon = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                Thread io=
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        DataOutputStream   out=null;
                        try {
                            out = new DataOutputStream(socket.getOutputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        while (true) {
                            String inputStr = null;
                            try {
                                inputStr = "Сообщение от сервера: "+inputCon.readLine();
                                out.writeUTF(inputStr );
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(inputStr);
                        }
                    }
                });

                threadsIO.add(io);
                io.start();

                int cnt=0;
                for (Thread thread: threadsIO) {
                    cnt++;
                    System.out.println("out cnt="+cnt);
                 //    thread.start();
                }


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

                threadsOI.add(oi);
                oi.start();

              //  int cnt=0;
               // for (Thread thread: threadsOI) {
               //     cnt++;
               //     System.out.println("out cnt="+cnt);
                   // thread.start();
             //   }



//.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}