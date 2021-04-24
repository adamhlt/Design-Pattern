package utils;

import javafx.scene.control.Alert;

/**
 * Class to manage popup
 *
 * @version 1.0
 */
public class PopupManager {
    /**
     * Create an error popup window containing a specific message
     *
     * @param message A string to display
     */
    public static void showAlert( String message ) {
        Alert alert = new Alert( Alert.AlertType.ERROR );
        alert.setHeaderText( message );
        alert.showAndWait();
    }
}
