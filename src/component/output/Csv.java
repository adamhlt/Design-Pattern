package component.output;

import model.Classroom;
import model.Setting;
import model.Student;
import utils.PopupManager;

import java.io.FileWriter;

/**
 * Generate a csv file from classroom datas
 *
 * @version 1.1
 */
public class Csv implements Generator {
    @Override
    public void Generate( Classroom classroom , Setting setting ) {
        try {
            FileWriter csv = new FileWriter( classroom.getName() + ".csv" );
            csv.append( "ID" + ";" + "Nom" + ";" + "Temps de connexion" + "\n" );
            for( Student student : classroom.getStudents() ) {
                csv.append( student.getId() );
                csv.append( ";" );
                csv.append( student.getIdentity() );
                csv.append( ";" );
                csv.append( String.valueOf( student.getTotalAttendanceDuration() ) );
                csv.append( "\n" );
            }
            csv.flush();
            csv.close();
        } catch( Exception e ) {
            PopupManager.showError( "Erreur lors de la création du fichier !" );
        }
    }
}
