package hhh.irp2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Listener {
    private Map<String, Node> map;

    public Listener(Map<String, Node> map) {
        this.map = map;
    }

    public void addListeners(){
        addListenersForNickname("Oby");
        addListenersForNickname("Ns");
        addListenersForNickname("Dm");
        addListenersForNickname("Em");
        addListenerForMainButton();
    }

    private void addListenersForNickname(String nickname){
        Button bMinus = (Button)map.get("bMinus"+nickname);
        bMinus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox block = (VBox)map.get("block"+nickname);
                if(block.getChildren().size()>4){
                    block.getChildren().remove(block.getChildren().size()-1);
                }
            }
        });

        Button bPlas = (Button)map.get("bPlas"+nickname);
        bPlas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox block = (VBox)map.get("block"+nickname);
                block.getChildren().add(TableViewSample.giveGPWithRadioButton());
            }
        });
    }

    private void addListenerForMainButton(){
        Button mainButton = (Button)map.get("mainButton");

        VBox blockOby = (VBox)map.get("blockOby");
        VBox blockNs = (VBox)map.get("blockNs");
        VBox blockDm = (VBox)map.get("blockDm");
        VBox blockEm = (VBox)map.get("blockEm");

        mainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Label label = (Label)map.get("label");

                DatePicker datePicker = (DatePicker) map.get("datePicker");
                LocalDate date = datePicker.getValue();

                ExelUtil.createSchedule(map, date);

                ArrayList<MyArray> peopleInMonth = addPeopleFromTableToList(date.lengthOfMonth());
                WordUtil.createStatement(peopleInMonth, date);

                WordUtil.createReport(date);

                label.setText(label.getText()+"\n"+"документы готовы");
            }
        });
    }
    private ArrayList<MyArray> addPeopleFromTableToList(int lengthOfMonth){

        ArrayList<MyArray> peopleInMonth = new ArrayList<>();

        for (int i = 1; i <= lengthOfMonth; i++) {

            MyArray peopleInDay = new MyArray();
            peopleInDay.add(onePersonFromBlock("Oby", i));
            peopleInDay.add(onePersonFromBlock("Ns", i));
            peopleInDay.add(onePersonFromBlock("Dm", i));
            peopleInDay.add(onePersonFromBlock("Em", i));

            peopleInMonth.add(peopleInDay);
        }
        return peopleInMonth;
    }
    private String onePersonFromBlock(String nickname, int day){

        VBox block = (VBox)map.get("block"+nickname);

        for (int j = 3; j < block.getChildren().size(); j++) {
            GridPane gridPane = (GridPane) block.getChildren().get(j);
            TextField textField = (TextField) gridPane.getChildren().get(0);
            RadioButton radioButton = (RadioButton) gridPane.getChildren().get(day);
            if( radioButton.isSelected() ){
                return textField.getText();
            }
        }
        return "не задан человек в смене "+day;
    }
}
