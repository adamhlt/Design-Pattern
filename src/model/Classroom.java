package model;

import utils.StudentIDServer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Classroom
{
    private String _name;
    private LocalDateTime _begin;
    private LocalDateTime _end;
    private final ArrayList<Student> _students;

    public Classroom() { this._students = new ArrayList<>(); }

    public void addStudentInfo( String identite, LocalDateTime instant){
        String id = StudentIDServer.getId( identite );
        for( Student student : _students )
            if( student.getId().equals( id ) ){
                student.addPeriod( instant );
                return;
            }
        Student student = new Student( id , identite );
        student.addPeriod( instant );
        _students.add( student );
    }

    public void setName( String _name) { this._name = _name; }

    public void setBegin( LocalDateTime _begin) {
        this._begin = _begin;
        for (Student student : this._students)
        {
            Iterator<LocalDateTime> iterator = student.getEventList().iterator();
            boolean closed = student.isClosed();
            while (iterator.hasNext())
            {
                LocalDateTime time = iterator.next();
                if (time.toLocalTime().isBefore(this._begin.toLocalTime()))
                    iterator.remove();
            }
            if (closed != student.isClosed())
                student.getEventList().addFirst(this._begin);
            student.getTotalAttendanceDuration();
        }
    }

    public void setEnd( LocalDateTime _end) {
        this._end = _end;
        for (Student student : this._students)
        {
            student.getEventList().removeIf(time -> time.toLocalTime().isAfter(this._end.toLocalTime()));
            if (!student.isClosed())
                student.getEventList().addLast(this._end);
            student.getTotalAttendanceDuration();
        }
    }

    public String getName() {
        return _name;
    }

    public ArrayList<Student> getStudents() {
        return _students;
    }

    public LocalDate getDate() {
        assert _students.size() != 0;
        return _students.get( 0 ).getFirstEvent().toLocalDate();
    }

    public LocalTime getMinConnection(){
        LocalDateTime min = null;
        for( Student student : _students ){
            LocalDateTime instant =  student.getFirstEvent();
            if( min == null || instant.isBefore( min ) )
                min = instant;
        }
        assert min != null;
        return min.toLocalTime();
    }

    public LocalTime getMaxConnection(){
        LocalDateTime max = null;
        for( Student student : _students ){
            LocalDateTime instant =  student.getLastEvent();
            if( max == null || instant.isAfter( max ) )
                max = instant;
        }
        assert max != null;
        return max.toLocalTime();
    }
}
