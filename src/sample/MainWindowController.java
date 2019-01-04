package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;

public class MainWindowController {

    @FXML
    private TextField directory;

    @FXML
    void onRenamePicturesAction(ActionEvent event) {
        Navigator.loadVista(Navigator.PICTURE_RENAME_WINDOW);
    }

    @FXML
    void onMallAction(ActionEvent event) {Navigator.loadVista(Navigator.MALL_WINDOW);
    }
}