package controller;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller
{
    private final Pane _uploadPane;
    private final Label _loadingLabel;
    private final Label _nameFileLabel;
    private final Label _dateFileLabel;
    private final Label _minFileLabel;
    private final Label _maxFileLabel;
    private final TextField _libelleText;
    private final TextField _minText;
    private final TextField _maxText;
    private final RadioButton _idRadio;
    private final RadioButton _nameRadio;
    private final CheckBox _nameCheck;
    private final CheckBox _idCheck;
    private final CheckBox _planningCheck;
    private final Button _generateButton;
    private File _file;
    final String IDLE_BUTTON_STYLE = "-fx-border-color : black; -fx-border-radius : 5";
    final String HOVERED_BUTTON_STYLE = "-fx-border-color : #005eff; -fx-border-radius : 5";

    public Controller(Parent root)
    {
        this._uploadPane = (Pane) root.lookup("#uploadPane");
        this._loadingLabel = (Label) root.lookup("#loadingLabel");
        this._nameFileLabel = (Label) root.lookup("#nameFileLabel");
        this._dateFileLabel = (Label) root.lookup("#dateFileLabel");
        this._minFileLabel = (Label) root.lookup("#minFileLabel");
        this._maxFileLabel = (Label) root.lookup("#maxFileLabel");
        this._libelleText = (TextField) root.lookup("#libelleText");
        this._minText = (TextField) root.lookup("#minText");
        this._maxText = (TextField) root.lookup("#maxText");
        this._idRadio = (RadioButton) root.lookup("#idRadio");
        this._nameRadio = (RadioButton) root.lookup("#nameRadio");
        this._nameCheck = (CheckBox) root.lookup("#nameCheck");
        this._idCheck = (CheckBox) root.lookup("#idCheck");
        this._planningCheck = (CheckBox) root.lookup("#planningCheck");
        this._generateButton = (Button) root.lookup("#generateButton");
        init();
    }

    private void init()
    {
        this._uploadPane.setOnMouseEntered(e -> _uploadPane.setStyle(HOVERED_BUTTON_STYLE));
        this._uploadPane.setOnMouseExited(e -> _uploadPane.setStyle(IDLE_BUTTON_STYLE));

        this._uploadPane.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() != this._uploadPane && dragEvent.getDragboard().hasFiles()) {
                dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            dragEvent.consume();
        });

        this._uploadPane.setOnDragDropped(dragEvent -> {
            Dragboard db = dragEvent.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                if (db.getFiles().get(0) != null)
                    loadingFile(db.getFiles().get(0));
            }
            dragEvent.setDropCompleted(success);
            dragEvent.consume();
        });

        this._uploadPane.setOnMouseClicked(mouseEvent -> {
            upload();
        });

        this._generateButton.setOnAction(actionEvent -> {
            generate();
        });

    }

    private void upload()
    {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null)
            loadingFile(selectedFile);
    }

    private void loadingFile(File file)
    {
        if (!file.isFile())
        {
            showAlert("Veuillez choisir un fichier valide !");
            return;
        }
        this._uploadPane.setDisable(true);
        this._loadingLabel.setVisible(false);
        this._nameFileLabel.setText("Fichier : " + file.getName());
        this._nameFileLabel.setVisible(true);
        this._dateFileLabel.setText("Date : ");
        this._dateFileLabel.setVisible(true);
        this._minFileLabel.setText("Heure Min : ");
        this._minFileLabel.setVisible(true);
        this._maxFileLabel.setText("Heure Max : ");
        this._maxFileLabel.setVisible(true);

        this._file = file;
    }

    private void generate()
    {
        if (this._file == null)
            return;

        var teamsProcessor = new TEAMSProcessor(this._file,"19/01/2021 à 10:15:00", "19/01/2021 à 11:45:00");
    }

    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
