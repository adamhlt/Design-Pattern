package component.output;

import model.Classroom;
import model.Student;
import utils.PopupManager;

import java.io.FileWriter;

public class Csv implements Generator
{
    @Override
    public void Generate( Classroom classroom )
    {
        try
        {
            FileWriter csv = new FileWriter(classroom.getName() + ".csv");
            csv.append("ID" + ";" + "Nom" + ";" + "Temps de connexion" + "\n");
            for (Student student : classroom.getStudents())
            {
                csv.append(student.getId());
                csv.append(";");
                csv.append(student.getName());
                csv.append(";");
                csv.append(String.valueOf(student.getTotalAttendanceDuration()));
                csv.append("\n");
            }
            csv.flush();
            csv.close();
        }
        catch (Exception e)
        {
            PopupManager.showAlert("Erreur lors de la création du fichier !");
        }
    }
}
