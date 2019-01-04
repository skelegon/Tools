package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane holder;

    @FXML
    public TextArea consoleWindow;


    public void setVista(Node node) {
        holder.getChildren().setAll(node);
        consoleWindow.setText("Welcome!");
    }

        @FXML
        void onHomeAction (ActionEvent event) {
            Navigator.loadVista(Navigator.MAIN_WINDOW);
    }
}