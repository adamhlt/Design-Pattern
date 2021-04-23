package model;

import utils.DateTimeConverter;
import utils.StudentIDServer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Classroom
{
    private String _name;
    private LocalDateTime _begin;
    private LocalDateTime _end;
    private final ArrayList<Student> _students;

    public Classroom() { this._students = new ArrayList<>(); }
    public Classroom(String name,String begin,String end){
        this._name = name;
        this._begin = DateTimeConverter.getLocalDateTimeFromString( begin );
        this._end = DateTimeConverter.getLocalDateTimeFromString( end );
        this._students = new ArrayList<>();
    }

    public void addInfo(String identite,LocalDateTime instant){
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

    public void set_name(String _name) { this._name = _name; }

    public void set_begin(LocalDateTime _begin) { this._begin = _begin; }

    public void set_end(LocalDateTime _end) { this._end = _end; }

    public String getName() {
        return _name;
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

    public LocalTime getMinConnection(){
        return LocalTime.of( 0,0 );
    }

    public LocalTime getMaxConnection(){
        return LocalTime.of( 0,0 );
    }
}
