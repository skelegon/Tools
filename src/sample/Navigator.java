package sample;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Navigator {

    public static final String MAIN    = "../resources/main.fxml";
    public static final String MAIN_WINDOW = "../resources/mainWindow.fxml";
    public static final String PICTURE_RENAME_WINDOW = "../resources/pictureRenameWindow.fxml";
    public static final String MALL_WINDOW = "../resources/mallWindow.fxml";

    private static MainController mainController;

    public static void setMainController(MainController mainController) {
        Navigator.mainController = mainController;
    }

    public static void loadVista(String fxml) {
        try {
            mainController.setVista(
                FXMLLoader.load(
                    Navigator.class.getResource(fxml)
                )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}