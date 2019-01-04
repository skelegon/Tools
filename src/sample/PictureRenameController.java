package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class PictureRenameController extends Window {

    @FXML
    private TextField directory;

    @FXML
    private TextField filesFound;

    @FXML
    public static String consoleWindow;

    public void onBrowseDirectoryAction(ActionEvent actionEvent) {
        File dir = new DirectoryChooser().showDialog(this);
        directory.setText(dir.getAbsolutePath());
        //TODO Show current dir in textfield
    }

    public void onRunAction(ActionEvent actionEvent) throws IOException {
        File dir = new File(directory.getText());
        PictureRename.pictureRename(dir);
    }
}