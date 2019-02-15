package ru.dartusha.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServer {

    private static final Pattern AUTH_PATTERN = Pattern.compile("^/auth (\\w+) (\\w+)$");

    private AuthService authService = new AuthServiceImpl();

    private Map<String, ClientHandler> clientHandlerMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start(Const.PORT);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream inp = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("New client connected!");

                try {
                    String authMessage = inp.readUTF();
                    Matcher matcher = AUTH_PATTERN.matcher(authMessage);
                    if (matcher.matches()) {
                        String username = matcher.group(1);
                        String password = matcher.group(2);
                        if (authService.authUser(username, password)) {
                            clientHandlerMap.put(username, new ClientHandler(username, socket, this));
                            out.writeUTF("/auth successful");
                            out.flush();
                            System.out.printf("Authorization for user %s successful%n", username);

                          //  sendMessage("ivan", "petr", "test");

                        } else {
                            System.out.printf("Authorization for user %s failed%n", username);
                            out.writeUTF("/auth fails");
                            out.flush();
                            socket.close();
                        }
                    } else {
                        System.out.printf("Incorrect authorization message %s%n", authMessage);
                        out.writeUTF("/auth fails");
                        out.flush();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String userTo, String userFrom, String msg) {
        // TODO реализовать отправку сообщения пользователю с именем username
        ClientHandler from=clientHandlerMap.get(userFrom);
        ClientHandler to=clientHandlerMap.get(userTo);
        System.out.printf("Сервер сообщение от пользователя %s к пользователю %s - %s",userFrom,userTo,msg);
        try {
            to.getOut().writeUTF(userFrom+":"+msg);
            to.getOut().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
