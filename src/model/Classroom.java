package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Classroom {
    private final String _name;
    private final LocalDate _date;
    private final LocalTime _begin;
    private final LocalTime _end;
    private final ArrayList<Student> _students;

    public Classroom( String name, LocalDate date, LocalTime begin, LocalTime end, ArrayList<Student> students ) {
        this._name = name;
        this._date = date;
        this._begin = begin;
        this._end = end;
        this._students = students;
    }

    public String getName() {
        return _name;
    }

    public LocalDate getDate() {
        return _date;
    }

    public LocalTime getBegin() {
        return _begin;
    }

    public LocalTime getEnd() {
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
