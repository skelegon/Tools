package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane holder;

    public void setVista(Node node) {
        holder.getChildren().setAll(node);
    }

        @FXML
        void onHomeAction (ActionEvent event) {
            Navigator.loadVista(Navigator.MAIN_WINDOW);
    }
}