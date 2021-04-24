package component.teams;

import model.Classroom;
import utils.DateTimeConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class to manage attance list
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
        LinkedList<String> attlist = loadTeamsCSVFile( file );
        return processList( attlist );
    }

    /**
     * Load csv file
     *
     * @param file A csv File
     * @return LinkedList of all csv file lines
     */
    private static LinkedList<String> loadTeamsCSVFile( File file ) {
        BufferedReader b = null;
        LinkedList<String> attlist = new LinkedList<>();
        try {
            b = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream( file ), StandardCharsets.UTF_16 ) );
            String readLine;
            attlist = new LinkedList<>();
            while( ( readLine = b.readLine() ) != null ) {
                attlist.add( readLine );
            }
            // last line is empty
            attlist.removeLast();
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
     * Proccess all lines of csv files
     *
     * @param attlist A linkedList of all csv file lines
     * @return A classroom fill with all students informations stored in the csv file
     */
    private static Classroom processList( LinkedList<String> attlist ) {
        Classroom classroom = new Classroom();
        Iterator<String> element = attlist.iterator();
        // first line unused
        element.next();
        // process all lines
        while( element.hasNext() ) {
            String input = element.next();

            String[] infos = input.split( "\t" );
            if( infos.length == 3 ) {
                String identite = infos[0];
                LocalDateTime instant = DateTimeConverter.getLocalDateTimeFromString( infos[2] );
                classroom.addStudentInfo( identite, instant );
            }
        }
        return classroom;
    }
}
