package ru.dartusha.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
Урок 4. Задание 1.
Создать окно для клиентской части чата: большое текстовое поле для отображения переписки в центре окна.
Однострочное текстовое поле для ввода сообщений и кнопка для отсылки сообщений на нижней панели.
Сообщение должно отсылаться либо по нажатию кнопки на форме, либо по нажатию кнопки Enter.
При «отсылке» сообщение перекидывается из нижнего поля в центральное.
 */

public class Chat extends Application {
    private Network network;

    public Chat(){

    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("face.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        Scene scene = new Scene(root);

        primaryStage.setTitle(Const.CHAT);
        primaryStage.setScene(scene);
        //primaryStage.setOnHidden(e -> controller.shutdown());
        primaryStage.show();


        FXMLLoader loaderSub = new FXMLLoader(getClass().getResource("LoginDialog.fxml"));
        Controller controllerSub = loaderSub.getController();
        GridPane page = (GridPane) loaderSub.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Авторизация");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene sceneSub = new Scene(page);
        dialogStage.setScene(sceneSub);
        dialogStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
