package model;

import component.list.ArrayList;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Represent a classroom
 *
 * @version 1.1
 */
public class Classroom {
    /**
     * String of the classroom name
     */
    private String _name;

    /**
     * String of the classroom source file name
     */
    private String _sourceName;

    /**
     * LocalTime of the classroom begin time
     */
    private LocalTime _begin;

    /**
     * LocalTime of the classroom end time
     */
    private LocalTime _end;

    /**
     * LocalDate of the classroom day
     */
    private LocalDate _date;

    /**
     * ArrayList of all classroom's students
     */
    private final ArrayList<Student> _students;

    /**
     * Generate an empty classroom
     */
    public Classroom() {
        this._students = new ArrayList<>();
    }

    /**
     * Set the classroom name
     */
    public void setName( String name ) {
        this._name = name;
    }

    /**
     * Get the classroom name
     *
     * @return A string of the name of the classroom
     */
    public String getName() {
        return _name;
    }

    /**
     * Set the source file name
     */
    public void setSourceName( String sourceName ) {
        this._sourceName = sourceName;
    }

    /**
     * Get the source file name
     *
     * @return A string of the name of the source file
     */
    public String getSourceName() {
        return _sourceName;
    }

    /**
     * Get begin of the course
     *
     * @return A localTime of the begin of the course
     */
    public LocalTime getBegin() {
        return _begin;
    }

    /**
     * Get end of the course
     *
     * @return A localTime of the end of the course
     */
    public LocalTime getEnd() {
        return _end;
    }

    /**
     * Get students list
     *
     * @return An arrayList of all students
     */
    public ArrayList<Student> getStudents() { return _students; }

    /**
     * Get duration of the classroom, the duration from the begin to the end
     *
     * @return A Duration of the classroom
     */
    public Duration getCourseDuration() {
        return Duration.between( this._begin, this._end );
    }

    /**
     * Add student event, if student doesn't exist in the classroom then add it otherwise add the event to
     * corresponding student
     *
     * @param identity String of the teams identity of a student
     * @param instant  LocalDateTime of the event
     */
    public void addStudentInfo( String identity, LocalDateTime instant ) {

        // if classroom date is null then deduct it from event
        if( _date == null )
            _date = instant.toLocalDate();

        for( Student student : _students )
            if( student.getIdentity().equals( identity ) ) {
                student.addEvent( instant.toLocalTime() );
                return;
            }
        Student student = new Student( identity );
        student.addEvent( instant.toLocalTime() );
        _students.add( student );
    }

    /**
     * Set the begin of the course, that allow to remove all events of students
     * that occur before the course
     *
     * @param begin A localDateTime representing the begin of the course
     */
    public void setBegin( LocalTime begin ) {
        this._begin = begin;
        for( Student student : this._students ) {
            boolean wasHeConnected = student.wasHeConnectedAt( this._begin );
            student.getEventList().removeIf( time -> time.isBefore( this._begin ) );
            if( wasHeConnected )
                student.getEventList().addFront( this._begin );
        }
    }

    /**
     * Set the end of the course, that allow to remove all events of students
     * that occur after the course
     *
     * @param end A localDateTime representing the end of the course
     */
    public void setEnd( LocalTime end ) {
        this._end = end;
        for( Student student : this._students ) {
            boolean wasHeConnected = student.wasHeConnectedAt( this._end );
            student.getEventList().removeIf( time -> time.isAfter( this._end ) );
            if( wasHeConnected )
                student.getEventList().add( this._end );
        }
    }

    /**
     * Get the date of the course from date stored in student event
     * Only work if there is student in the classroom
     *
     * @return A localDate of the day when the course occur
     */
    public LocalDate getDate() {
        return _date;
    }

    /**
     * Get the first ever connection in the course
     *
     * @return A localTime of connection event
     */
    public LocalTime getMinConnection() {
        LocalTime min = null;
        for( Student student : _students ) {
            LocalTime instant = student.getFirstEvent();
            if( min == null || instant.isBefore( min ) )
                min = instant;
        }
        assert min != null;
        return min;
    }

    /**
     * Get the last ever connection in the course
     *
     * @return A localTime of connection event
     */
    public LocalTime getMaxConnection() {
        LocalTime max = null;
        for( Student student : _students ) {
            LocalTime instant = student.getLastEvent();
            if( max == null || instant.isAfter( max ) )
                max = instant;
        }
        assert max != null;
        return max;
    }
}
