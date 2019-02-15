package ru.dartusha.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginDialog {
    public LoginDialog(Stage primaryStage) throws Exception {
        FXMLLoader loaderSub = new FXMLLoader(getClass().getResource("LoginDialog.fxml"));
        Controller controller = loaderSub.getController();
      //  loaderSub.setController( this );
    //    controller.setPrimaryStage(primaryStage);

        GridPane page = (GridPane) loaderSub.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Авторизация");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene sceneSub = new Scene(page);
        dialogStage.setScene(sceneSub);
        dialogStage.show();
    }
}