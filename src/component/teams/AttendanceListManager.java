package component.teams;

import component.list.ArrayList;
import model.Classroom;
import utils.DateTimeConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * Class to manage attendance list
 *
 * @version 1.0
 */
public class AttendanceListManager {
    /**
     * Generate classroom from csv file
     *
     * @param file A csv File
     * @return A classroom fill with all students informations stored in the csv file
     */
    public static Classroom GenerateClassroom( File file ) {
        ArrayList<String> attlist = loadTeamsCSVFile( file );
        return processList( attlist );
    }

    /**
     * Load csv file
     *
     * @param file A csv File
     * @return An arrayList of all csv file lines
     */
    private static ArrayList<String> loadTeamsCSVFile( File file ) {
        BufferedReader b = null;
        ArrayList<String> attlist = new ArrayList<>();
        try {
            b = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream( file ), StandardCharsets.UTF_16 ) );
            String readLine;
            while( ( readLine = b.readLine() ) != null )
                attlist.add( readLine );
        } catch( IOException fileNotFoundException ) {
            fileNotFoundException.printStackTrace();
        } finally {
            if( b != null ) {
                try {
                    b.close();
                } catch( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
        return attlist;
    }

    /**
     * Process all lines of csv files
     *
     * @param attlist An arraylist of all csv file lines
     * @return A classroom fill with all students informations stored in the csv file
     */
    private static Classroom processList( ArrayList<String> attlist ) {
        Classroom classroom = new Classroom();
        Iterator<String> element = attlist.iterator();
        // first line unused
        element.next();
        // process all lines
        do{
            String input = element.next();

            String[] infos = input.split( "\t" );
            if( infos.length == 3 ) {
                String identity = infos[0];
                LocalDateTime instant = DateTimeConverter.getLocalDateTimeFromString( infos[2] );
                classroom.addStudentInfo( identity, instant );
            }
        } while ( element.hasNext() );

        return classroom;
    }
}
