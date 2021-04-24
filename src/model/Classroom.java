package model;

import utils.StudentIDServer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represent a classroom
 *
 * @version 1.0
 */
public class Classroom {
    private String _name;
    private String _sourceName;
    private LocalDateTime _begin;
    private LocalDateTime _end;
    private final ArrayList<Student> _students;

    public void setName( String name ) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    public void setSourceName( String sourceName ) {
        this._sourceName = sourceName;
    }

    public String getSourceName() {
        return _sourceName;
    }

    public LocalDateTime getBegin() {
        return _begin;
    }

    public LocalDateTime getEnd() {
        return _end;
    }

    public ArrayList<Student> getStudents() {
        return _students;
    }

    public Classroom() {
        this._students = new ArrayList<>();
    }

    /**
     * Add student event :
     * If student doesn't exist in the classroom then add it
     * Else add the event to corresponding student
     *
     * @param indentity String of the teams identity of a student
     * @param instant   LocalDateTime of the event
     */
    public void addStudentInfo( String indentity, LocalDateTime instant ) {
        String id = StudentIDServer.getId( indentity );
        for( Student student : _students )
            if( student.getId().equals( id ) ) {
                student.addEvent( instant );
                return;
            }
        Student student = new Student( id, indentity );
        student.addEvent( instant );
        _students.add( student );
    }

    /**
     * Set the begin of the course, that allow to remove all events of students
     * that occur before the course
     *
     * @param _begin A localDateTime representing the begin of the course
     */
    public void setBegin( LocalDateTime _begin ) {
        this._begin = _begin;
        for( Student student : this._students ) {
            Iterator<LocalDateTime> iterator = student.getEventList().iterator();
            boolean closed = student.isClosed();
            while( iterator.hasNext() ) {
                LocalDateTime time = iterator.next();
                if( time.toLocalTime().isBefore( this._begin.toLocalTime() ) )
                    iterator.remove();
            }
            if( closed != student.isClosed() )
                student.getEventList().addFirst( this._begin );
            student.getTotalAttendanceDuration();
        }
    }

    /**
     * Set the end of the course, that allow to remove all events of students
     * that occur after the course
     *
     * @param _end A localDateTime representing the end of the course
     */
    public void setEnd( LocalDateTime _end ) {
        this._end = _end;
        for( Student student : this._students ) {
            student.getEventList().removeIf( time -> time.toLocalTime().isAfter( this._end.toLocalTime() ) );
            if( !student.isClosed() )
                student.getEventList().addLast( this._end );
            student.getTotalAttendanceDuration();
        }
    }

    /**
     * Get the date of the course from date stored in student event
     * Only work if there is student in the classroom
     *
     * @return A localDate of the day when the course occur
     */
    public LocalDate getDate() {
        assert _students.size() != 0;
        return _students.get( 0 ).getFirstEvent().toLocalDate();
    }

    /**
     * Get the first ever connection in the course
     *
     * @return A localTime of connection event
     */
    public LocalTime getMinConnection() {
        LocalDateTime min = null;
        for( Student student : _students ) {
            LocalDateTime instant = student.getFirstEvent();
            if( min == null || instant.isBefore( min ) )
                min = instant;
        }
        assert min != null;
        return min.toLocalTime();
    }

    /**
     * Get the last ever connection in the course
     *
     * @return A localTime of connection event
     */
    public LocalTime getMaxConnection() {
        LocalDateTime max = null;
        for( Student student : _students ) {
            LocalDateTime instant = student.getLastEvent();
            if( max == null || instant.isAfter( max ) )
                max = instant;
        }
        assert max != null;
        return max.toLocalTime();
    }

    /**
     * Get duration of the classroom, the duration from the begin to the end
     *
     * @return A Duration of the classroom
     */
    public Duration getCourseDuration() {
        return Duration.between( this._begin, this._end );
    }
}
