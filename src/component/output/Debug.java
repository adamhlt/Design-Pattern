package component.output;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;
import model.Classroom;
import model.Student;

public class Debug implements Generator{
    @Override
    public void Generate( Classroom classroom )
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Debug Dialog");
        alert.setHeaderText("Affichage Debug");
        alert.setContentText("Détails de tous les élèves :");

        TextArea textArea = new TextArea(getDetails(classroom));
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().setExpanded(true);
        alert.showAndWait();
    }

    private String getDetails(Classroom classroom)
    {
        StringBuilder details = new StringBuilder();
        for (Student student : classroom.getStudents())
        {
            details.append("ID : ");
            details.append(student.getId());
            details.append(" Nom : ");
            details.append(student.getName());
            details.append(" Temps Total : ");
            details.append(student.getTotalAttendanceDuration());
            details.append("\n");
        }
        return details.toString();
    }
}