package model;

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
