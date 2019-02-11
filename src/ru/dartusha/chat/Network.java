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
                            //TODO После закрытия close() выпадает здесь в exception. Хотя по идее уже не должен
                            //был сюда переходить из-за interrupted. Решить не удалось
                            String msg = in.readUTF();
                            // SwingUtilities.invokeLater
                            Platform.runLater
                                    (new Runnable() {
                                        @Override
                                        public void run() {
                                            messageSender.submitMessage(Const.SERVER, msg);
                                        }
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        });

        this.receiver.start();

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
        in.close();
        out.close();
        socket.close();
        receiver.interrupt();
    }
}