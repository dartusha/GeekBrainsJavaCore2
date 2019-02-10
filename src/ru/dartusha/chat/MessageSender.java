package ru.dartusha.chat;

public interface MessageSender {

    void submitMessage(String user, String message);
}