package ru.dartusha.chat;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
            network.sendMessage(text);
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


    @FXML
    /*public void exitApplication(ActionEvent event) {
        System.out.println("stop");
        try {
            network.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    public void shutdown() {
        // cleanup code here...
        System.out.println("Stop");
        //TODO если добавлять network.close то при закрытии окна возникают ошибки связанные с закрытым сокетом. Нужно удалять сокет из массивов. Сделать на будущее
        /*
        try {
            network.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        // note that typically (i.e. if Platform.isImplicitExit() is true, which is the default)
        // closing the last open window will invoke Platform.exit() anyway
        Platform.exit();
    }

}
