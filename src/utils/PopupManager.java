package utils;

import javafx.scene.control.Alert;

public class PopupManager
{
    public static void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
