package component.teams;

import java.io.File;

import component.output.Generator;
import component.sort.Sort;
import model.Classroom;
import utils.DateTimeConverter;

/**
 * Class to process file and setting from UI
 *
 * @version 1.0
 */
public class Processor {
    private final Classroom _classroom;
    private Generator _generator;
    private Sort _sorter;

    /**
     * Create a Processor from a csv file
     *
     * @param file A csv File containing all teams informations
     */
    public Processor( File file ) {
        this._classroom = AttendanceListManager.GenerateClassroom( file );
        this._classroom.setSourceName( file.getName() );
    }

    /**
     * Process all data and setting to generate specific output using the provided generator
     *
     * @param courseName A String representing the course name
     * @param begin      A string of the begin of the course using pattern "dd/MM/yyyy à HH:mm:ss"
     * @param End        A string of the end of the course using pattern "dd/MM/yyyy à HH:mm:ss"
     */
    public void Process( String courseName, String begin, String End ) {
        this._classroom.setName( courseName );
        this._classroom.setBegin( DateTimeConverter.getLocalDateTimeFromString( begin ) );
        this._classroom.setEnd( DateTimeConverter.getLocalDateTimeFromString( End ) );
        this._sorter.sortStudent( this._classroom );
        this._generator.Generate( this._classroom );
    }

    public Classroom getClassroom() {
        return _classroom;
    }

    public void setGenerator( Generator generator ) { this._generator = generator; }

    public void setSorter( Sort sorter ) { this._sorter = sorter; }
}
