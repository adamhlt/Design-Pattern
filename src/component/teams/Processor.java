package component.teams;

import java.io.File;

import component.output.Generator;
import model.Classroom;
import utils.DateTimeConverter;

public class Processor
{
    private final String _fileName;
    private final Classroom _classroom;
    private Generator _generator;

    public Processor(File file){
        this._fileName = file.getName();
        this._classroom = AttendanceListManager.GenerateClassroom(file);
    }

    public void Process(String courseName, String begin, String End){
        this._classroom.setName( courseName );
        this._classroom.setBegin( DateTimeConverter.getLocalDateTimeFromString( begin ) );
        this._classroom.setEnd( DateTimeConverter.getLocalDateTimeFromString( End ) );
        this._generator.Generate( this._classroom );
    }

    public Classroom getClassroom(){
        return _classroom;
    }

    public String getFileName(){
        return _fileName;
    }

    public void setGenerator(Generator generator) { this._generator = generator; }
}
