package ru.dartusha.chat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, MessageSender {

    @FXML
    public TextField tfMessage;

    @FXML
    public ListView lvMessages;

    @FXML
    public Button btSendMessage;

    Stage primaryStage;

    private ObservableList<String> messageList;

    private Network network;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageList = FXCollections.observableArrayList();
        lvMessages.setItems(messageList);

        try {
            network = new Network("localhost", 7777,  this);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void onSendMessageClicked() {
        String text = tfMessage.getText();
        if (text != null && !text.isEmpty()) {
           // messageList.add(text);

            network.sendMessage(text);

          //  lvMessages.setItems(messageList);
            tfMessage.clear();
            tfMessage.requestFocus();
        }
    }

    public void setOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onSendMessageClicked();
        }
    };

    @Override
    public void submitMessage(String user, String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        Message msg = new Message(user, message);
        messageList.add(message);
        lvMessages.setItems(messageList);
    }
}

//TODO на закрытии сделать network.close