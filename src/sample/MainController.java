package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    public StackPane holder;

    @FXML

    private TextArea consoleWindow;

    public void setConsoleWindow(TextArea consoleWindow){
        this.consoleWindow = consoleWindow;
    }

    public TextArea getConsoleWindow(){
        return this.consoleWindow;
    }

    private final static String newline = "\n";


    public void appendText(String text) {
       getConsoleWindow().appendText(text+newline);
    }

    public void setVista(Node node) {
        holder.getChildren().setAll(node);
    }

    @FXML
    void onHomeAction (ActionEvent event) {
        Navigator.loadVista(Navigator.MAIN_WINDOW);
        appendText("Returning to main window");
    }
}