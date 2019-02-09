import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/*
Урок 4. Задание 1.
Создать окно для клиентской части чата: большое текстовое поле для отображения переписки в центре окна.
Однострочное текстовое поле для ввода сообщений и кнопка для отсылки сообщений на нижней панели.
Сообщение должно отсылаться либо по нажатию кнопки на форме, либо по нажатию кнопки Enter.
При «отсылке» сообщение перекидывается из нижнего поля в центральное.
 */

public class Chat extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        TextArea textArea = new TextArea();
        TextField textField=new TextField();

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);

        primaryStage.setTitle("Чат");

        Button btn = new Button();
        btn.setText("Отправить");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.appendText(textField.getText()+"\n");
                textField.setText("");
            }
        });

        root.getChildren().add(textArea);
        root.getChildren().add(textField);
        root.getChildren().add(btn);

        Scene scene = (new Scene(root, 300, 275));

        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    textArea.appendText(textField.getText()+"\n");
                    textField.setText("");
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
