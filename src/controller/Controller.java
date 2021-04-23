package controller;

import component.teams.Processor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.Classroom;

import java.io.File;

public class Controller
{
    private static final String IDLE_BUTTON_STYLE = "-fx-border-color : black; -fx-border-radius : 5";
    private static final String HOVERED_BUTTON_STYLE = "-fx-border-color : #005eff; -fx-border-radius : 5";

    private final Label _loadingLabel;
    private final Label _nameFileLabel;
    private final Label _dateFileLabel;
    private final Label _minFileLabel;
    private final Label _maxFileLabel;
    private final RadioButton _idRadio;
    private final RadioButton _nameRadio;
    private final CheckBox _nameCheck;
    private final CheckBox _idCheck;
    private final CheckBox _planningCheck;
    private final Button _generateButton;

    private TextField _libelleText;
    private TextField _minText;
    private TextField _maxText;
    private Processor _processor;
    private Pane _uploadPane;

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

        this._uploadPane.setOnMouseEntered(e -> _uploadPane.setStyle(HOVERED_BUTTON_STYLE));
        this._uploadPane.setOnMouseExited(e -> _uploadPane.setStyle(IDLE_BUTTON_STYLE));
        this._uploadPane.setOnDragOver(uploadStart);
        this._uploadPane.setOnDragDropped(uploadDone);
        this._uploadPane.setOnMouseClicked(upload);
        this._generateButton.setOnAction(generate);
    }

    EventHandler<MouseEvent> upload = mouseEvent ->
    {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null)
            loadingFile(selectedFile);
    };

    EventHandler<DragEvent> uploadDone = dragEvent ->
    {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasFiles())
        {
            success = true;
            if (db.getFiles().get(0) != null)
                loadingFile(db.getFiles().get(0));
        }
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    };

    EventHandler<DragEvent> uploadStart = dragEvent ->
    {
        if (dragEvent.getGestureSource() != this._uploadPane && dragEvent.getDragboard().hasFiles())
        {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    };

    EventHandler<ActionEvent> generate = actionEvent ->
    {
        if (this._libelleText.getAccessibleText().toString().isEmpty())
        {
            this._libelleText.requestFocus();
            utils.PopupManager.showAlert("Veullez rensigner un libellé !");
            return;
        }

        if (this._minText.getAccessibleText().toString().isEmpty())
        {
            this._minText.requestFocus();
            utils.PopupManager.showAlert("Veullez rensigner le début du cours !");
            return;
        }

        if (this._maxText.getAccessibleText().toString().isEmpty())
        {
            this._maxText.requestFocus();
            utils.PopupManager.showAlert("Veullez rensigner la fin du cours !");
            return;
        }

        //TODO appeler Processor.process()
    };

    private void loadingFile(File file)
    {
        if (!file.isFile() || !utils.FileManager.getFileExtension(file).equals(".csv"))
        {
            utils.PopupManager.showAlert("Veuillez choisir un fichier valide !");
            return;
        }

        this._processor = new Processor( file );

        displayFileDetails();
    }

    private void displayFileDetails()
    {
        Classroom classroom = this._processor.getClassroom();

        this._nameFileLabel.setText("Fichier : " + this._processor.getFileName());
        this._dateFileLabel.setText("Date : " + classroom.getDate().toString());
        this._minFileLabel.setText("Heure Min : " + classroom.getMinConnection().toString());
        this._maxFileLabel.setText("Heure Max : " + classroom.getMaxConnection().toString());

        this._uploadPane.setDisable(true);
        this._loadingLabel.setVisible(false);
        this._nameFileLabel.setVisible(true);
        this._dateFileLabel.setVisible(true);
        this._minFileLabel.setVisible(true);
        this._maxFileLabel.setVisible(true);
        this._generateButton.setDisable(false);
    }
}
