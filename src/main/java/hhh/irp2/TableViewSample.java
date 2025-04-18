package hhh.irp2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;

// класс для отрисовки интерфейса
    public class TableViewSample extends Application {

        private HashMap<String, Node> map = new HashMap<>();

        // private TableView table = new TableView();
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) {

            HBox root = new HBox();
            VBox mainVBox = new VBox();
            //mainVBox.setPrefWidth(2000);
            mainVBox.setAlignment(Pos.TOP_CENTER);

            mainVBox.getChildren().add(givMeBlock("ОБУ", "Oby"));
            mainVBox.getChildren().add(givMeBlock("Начальник смены","Ns"));
            mainVBox.getChildren().add(givMeBlock("Дежурный механик КВ/УКВ", "Dm"));
            mainVBox.getChildren().add(givMeBlock("Дежурный электромеханик", "Em"));
            mainVBox.getChildren().add(giveMeMainButton());

            Label label = new Label();
            map.put("label",label);
            mainVBox.getChildren().add(label);


            Listener listener = new Listener(map);
            listener.addListeners();

            root.setPrefWidth(1340);
            root.setAlignment(Pos.TOP_CENTER);
            root.getChildren().add(mainVBox);

            ScrollPane scrollPane = new ScrollPane(root);
            scrollPane.setPrefViewportWidth(1000);

            Scene scene = new Scene(scrollPane, 2000,2000);// root
            stage.setTitle("График дежурств        ");
            stage.setScene(scene);
            stage.show();
        }

        private VBox givMeBlock(String name, String nickname){

            VBox vbox = new VBox();
            vbox.setAlignment(Pos.TOP_CENTER);
            map.put("block"+nickname, vbox);
            // 0
            vbox.getChildren().add(whitespace());
            // 1
            vbox.getChildren().add(new Label(name));

            // 2 Записываем числа в первую строку
            GridPane gridpane = giveGPWithSomeWidth();

            TextField tf = new TextField();
            tf.setMinWidth(265.0);
            tf.setVisible(false);

            gridpane.add(tf, 0, 0);

            for (int i = 1; i < 32; i++) {
                gridpane.add(new Label(""+i), i+1,0);
            }
            vbox.getChildren().add(gridpane);

            // 3 Записываем radiobutton во вторую строку
            gridpane = giveGPWithRadioButton();

            // 3 Добавляем кнопки + и -
            Button bMinus = new Button("-");
            bMinus.setMinWidth(30.0);
            map.put("bMinus"+nickname, bMinus);
            gridpane.add(bMinus, 34,0);

            Button bPlas = new Button("+");
            bPlas.setMinWidth(30.0);
            map.put("bPlas"+nickname, bPlas);
            gridpane.add(bPlas, 36,0);

            vbox.getChildren().add(gridpane);

            return vbox;
        }

        private VBox giveMeMainButton(){

            HBox hBox = new HBox();
            HBox indent = new HBox();
            indent.setMinWidth(740);
            hBox.getChildren().add(indent);

            DatePicker datePicker = new DatePicker(LocalDate.now());
            map.put("datePicker", datePicker);
            hBox.getChildren().add(datePicker);

            indent = new HBox();
            indent.setMinWidth(30);
            hBox.getChildren().add(indent);

            Button button = new Button("создать");
            hBox.getChildren().add(button);
            map.put("mainButton", button);

            VBox vBox = new VBox();
            vBox.getChildren().add(whitespace());
            vBox.getChildren().add(hBox);
            vBox.getChildren().add(whitespace());

            return vBox;
        }

        private HBox whitespace(){
            HBox whitespace = new HBox();
            whitespace.setMinHeight(20.0);
            return whitespace;
        }

        private static GridPane giveGPWithSomeWidth(){
            GridPane gridpane = new GridPane();
            // Задаем ширину столбцов
            gridpane.getColumnConstraints().add(new ColumnConstraints(250));
            for (int i = 1; i < 38; i++) {
                gridpane.getColumnConstraints().add(new ColumnConstraints(25));
            }
            return gridpane;
        }

        public static GridPane giveGPWithRadioButton(){
            GridPane gridpane = giveGPWithSomeWidth();

            TextField tf = new TextField();
            tf.setMinWidth(265.0);
            gridpane.add(tf, 0, 0);

            for (int i = 1; i < 32; i++) {
                gridpane.add(new RadioButton(), i+1,0);
            }
            return gridpane;
        }
    }

