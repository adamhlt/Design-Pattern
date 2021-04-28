package component.output;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.StageStyle;
import model.Classroom;
import model.Setting;
import model.Student;

/**
 * Generate a debug popup from classroom datas
 *
 * @version 1.1
 */
public class Debug implements Generator {

    private static final String ID = "ID";
    private static final String IDENTITY = "Identité";
    private static final String TOTAL_TIME = "Temps total";
    private static final String PERCENT = "Pourcentage";

    private Setting _setting;

    @Override
    public void Generate( Classroom classroom, Setting setting ) {
        this._setting = setting;

        Alert alert = new Alert( Alert.AlertType.INFORMATION );
        alert.initStyle( StageStyle.UTILITY );
        alert.setTitle( "Debug Dialog Cours" );
        alert.setHeaderText( "Affichage Debug Cours : " + classroom.getName() );
        alert.setContentText( "Détails de tous les élèves :" );

        TextArea textArea = new TextArea( getDetails( classroom ) );
        textArea.setStyle( "-fx-font-family: monospace" );
        textArea.setEditable( false );
        textArea.setWrapText( true );

        textArea.setMaxWidth( Double.MAX_VALUE );
        textArea.setMaxHeight( Double.MAX_VALUE );
        GridPane.setVgrow( textArea, Priority.ALWAYS );
        GridPane.setHgrow( textArea, Priority.ALWAYS );

        GridPane expContent = new GridPane();
        expContent.setMaxWidth( Double.MAX_VALUE );
        expContent.add( textArea, 0, 1 );

        alert.getDialogPane().setExpandableContent( expContent );
        alert.getDialogPane().setExpanded( true );
        alert.showAndWait();
    }

    private String getDetails( Classroom classroom ) {
        int maxIdLength = ID.length();
        int maxIdentityLength = IDENTITY.length();
        int maxAttendanceDurationLength = TOTAL_TIME.length();
        int maxPercentLength = PERCENT.length();

        for( Student student : classroom.getStudents() ) {
            int idLength = student.getId().length();
            int identityLength = student.getIdentity().length();
            int attendanceDurationLength = Long.toString( student.getTotalAttendanceDuration() ).length();
            int percentLength = Long.toString( student.getAttendancePercent( classroom.getCourseDuration() ) ).length();

            if( idLength > maxIdLength )
                maxIdLength = idLength;
            if( identityLength > maxIdentityLength )
                maxIdentityLength = identityLength;
            if( attendanceDurationLength > maxAttendanceDurationLength )
                maxAttendanceDurationLength = attendanceDurationLength;
            if( percentLength > maxPercentLength )
                maxPercentLength = percentLength;
        }

        StringBuilder details = new StringBuilder();

        if( !this._setting.isWithoutID() )
            details.append( setSpace( "ID", maxIdLength, " | " ) );
        if( !this._setting.isWithoutIdentity() )
            details.append( setSpace( "Identité", maxIdentityLength, " | " ) );
        details.append( setSpace( "Temps total", maxAttendanceDurationLength, " | " ) );
        details.append( setSpace( "Pourcentage", maxPercentLength, "\n" ) );

        for( Student student : classroom.getStudents() ) {
            if( !this._setting.isWithoutID() )
                details.append( setSpace( student.getId(), maxIdLength, " | " ) );
            if( !this._setting.isWithoutIdentity() )
                details.append( setSpace( student.getIdentity(), maxIdentityLength, " | " ) );
            details.append( setSpace( Long.toString( student.getTotalAttendanceDuration() ),
                    maxAttendanceDurationLength, " | " ) );
            details.append( setSpace( Long.toString( student.getAttendancePercent( classroom.getCourseDuration() ) ) + "%",
                    maxPercentLength, "\n" ) );
        }
        return details.toString();
    }

    private String setSpace( String value, int length, String end ) {
        return value + " ".repeat( length - value.length() ) + end;
    }
}