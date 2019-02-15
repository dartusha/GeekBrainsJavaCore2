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

    public Network network;

    @FXML
    public TextField tfLogin;

    @FXML
    public TextField pfPassword;

    @FXML
    public Button btLogin;

    @FXML
    public Text txtLoginResult;

    String usrCur;

    public void onLoginClicked(ActionEvent event) {
        network = null;
        try {
            network = new Network(Const.LOCAL_HOST, Const.PORT, (MessageSender) this);
            System.out.println(tfLogin.getText()+ String.valueOf(pfPassword.getText()));
            usrCur=tfLogin.getText();
            network.authorize(tfLogin.getText(), String.valueOf(pfPassword.getText()));
            txtLoginResult.setText("");
            Stage stage = (Stage) btLogin.getScene().getWindow();
            stage.hide();
            System.out.println("UsrCur:"+usrCur);

        } catch (IOException ex) {
            ex.printStackTrace();
            txtLoginResult.setText("Ошибка сети");
        }
        catch (AuthException ex) {
            txtLoginResult.setText("Ошибка авторизации");
        }
    }


    public Network getNetwork() {
        return network;
    }


    public boolean isAuthSuccessful() {
        return network != null;
    }



    //private Network network;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageList = FXCollections.observableArrayList();

        // lvMessages.setItems(usersList);


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
        //TODO Проблема здесь. Хотя раньше на onLoginClicked инициализирую логин текущего пользователя usrCur далее при обращении к нему из другой процедуры он возвращается как null. Из-за этого не могу поставить источника сообщения и отправить его.
        System.out.println("UsrCur 2:"+usrCur);
       // System.out.println(network);
        System.out.println("to:"+ text );
        Message msg = new Message("","", tfMessage.getText());
        submitMessage(msg);
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
      //  System.out.println(message.getText());
        messageList.add(message.getText());
        lvMessages.setItems(messageList);
        network.sendMessage(message.getText());
    }

    public void onLvUsersClick() {
        String str=lvUsers.getSelectionModel().getSelectedItem().toString();
        tfMessage.clear();
        tfMessage.setText("/"+str);
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


