package ru.dartusha.chat;

import javafx.application.Platform;
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

    //private Network network;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageList = FXCollections.observableArrayList();
        lvMessages.setItems(messageList);


       // try {
       //     network = new Network(Const.LOCAL_HOST, Const.PORT,  this);
       // } catch (IOException e) {
       //     e.printStackTrace();
       //     System.exit(-1);
       //}
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void onSendMessageClicked() {
        String text = tfMessage.getText();
        if (text != null && !text.isEmpty()) {
           // LoginController.getNetwork().sendMessage(text);
            tfMessage.clear();
            tfMessage.requestFocus();
        }
    }

    public void setOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onSendMessageClicked();
        }
    };

    public void submitMessage(Message message) {
        //Message msg = new Message()
        messageList.add(message.getText());
        lvMessages.setItems(messageList);
    }


    //public void shutdown() {
    //   try {
     //       network.sendMessage(Const.CMD_CLOSED);
     //       network.close();
     //   } catch (IOException e) {
     //      e.printStackTrace();
     //   }
      //  Platform.exit();
    }


