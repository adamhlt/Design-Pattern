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
        //TODO supprimer tout les evenements ( events ) qui sont avant cette date

    }

    public void setEnd( LocalDateTime _end) {
        this._end = _end;
        //TODO supprimer tout les evenements ( events ) qui sont apres cette
        //TODO si l'eleve ne s'est pas deco ( if isClosed() == false ) alors ajouter un event avec la date de fin
    }

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
