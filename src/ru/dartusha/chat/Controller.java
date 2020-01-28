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
import javafx.scene.text.Text;
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
    public ListView lvUsers;

    @FXML
    public Button btSendMessage;

    Stage primaryStage;

    private ObservableList<String> messageList;



    //private Network network;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageList = FXCollections.observableArrayList();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void onSendMessageClicked() {
        String text = tfMessage.getText();
        Message msg=null;

        msg = new Message("", DataProcess.getCuruser(), tfMessage.getText());
        submitMessage(msg);
        DataProcess.getNetwork().sendMessage(msg.getText());
        if (text != null && !text.isEmpty()) {
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
        messageList.add(message.getText().replace("/w","Для "));
        lvMessages.setItems(messageList);
    }

    public void onLvUsersClick() {
        String str="/w "+lvUsers.getSelectionModel().getSelectedItem().toString();
        tfMessage.clear();
        tfMessage.setText(str);
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


