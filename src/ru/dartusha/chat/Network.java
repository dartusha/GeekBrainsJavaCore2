package ru.dartusha.chat;

import javafx.application.Platform;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network implements Closeable {

    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final MessageSender messageSender;
    private final Thread receiver;



    public Network(String hostName, int port, MessageSender messageSender) throws IOException {
        this.socket = new Socket(hostName, port);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        this.messageSender = messageSender;

        this.receiver = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String msg = in.readUTF();

                       // SwingUtilities.invokeLater
                        Platform.runLater
                                (new Runnable() {
                            @Override
                            public void run() {
                               // System.out.println("Сообщение : " + msg);
                                messageSender.submitMessage("server", msg);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        receiver.start();

    }

    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        receiver.interrupt();
        socket.close();
    }
}