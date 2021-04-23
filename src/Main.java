import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource( "fxml/main.fxml" ));
        primaryStage.setTitle("Teams Project");
        primaryStage.setScene(new Scene(root, 533, 631));
        primaryStage.setResizable(false);
        primaryStage.show();
        root.requestFocus();
        Controller controller = new Controller(root);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
