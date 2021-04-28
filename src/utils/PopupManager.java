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
    public static void showError( String message ) {
        Alert alert = new Alert( Alert.AlertType.ERROR );
        alert.setHeaderText( message );
        alert.setTitle( "Erreur" );
        alert.showAndWait();
    }

    /**
     * Create a success popup window containing a specific message
     *
     * @param message A string to display
     */
    public static void showSuccess( String message ) {
        Alert alert = new Alert( Alert.AlertType.NONE );
        alert.setHeaderText( message );
        alert.setTitle( "Succès" );
        alert.showAndWait();
    }
}
