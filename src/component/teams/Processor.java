package component.teams;

import java.io.File;
import java.time.LocalDateTime;

import component.output.Generator;
import component.sort.Sorter;
import model.Classroom;
import model.Setting;

/**
 * Class to process file and setting from UI
 *
 * @version 1.0
 */
public class Processor {
    /**
     * Classroom to be processed
     */
    private final Classroom _classroom;

    /**
     * Generator that will be use to generate the output result
     */
    private Generator _generator;

    /**
     * Sorter that will be use to sort the output result
     */
    private Sorter _sorter;

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
     * Process all data and setting to generate specific output using the provided generator and setting
     *
     * @param courseName A string representing the course name
     * @param setting    A Setting object to store some generating setting
     * @param begin      A string of the begin of the course using pattern "dd/MM/yyyy à HH:mm:ss"
     * @param end        A string of the end of the course using pattern "dd/MM/yyyy à HH:mm:ss"
     * @see Setting
     */
    public void Process( String courseName, Setting setting, LocalDateTime begin, LocalDateTime end ) {
        this._classroom.setName( courseName );
        this._classroom.setBegin( begin.toLocalTime() );
        this._classroom.setEnd( end.toLocalTime() );
        this._sorter.sortStudent( this._classroom );
        this._generator.Generate( this._classroom, setting );
    }

    /**
     * Get classroom
     *
     * @return Classroom
     */
    public Classroom getClassroom() {
        return _classroom;
    }

    /**
     * Set generator
     *
     * @param generator that will be use to generate the output result
     * @see Generator
     */
    public void setGenerator( Generator generator ) { this._generator = generator; }

    /**
     * Set sorter
     *
     * @param sorter that will be use to sort the output result
     * @see Sorter
     */
    public void setSorter( Sorter sorter ) { this._sorter = sorter; }
}
