package component.teams;

import java.io.File;

import component.output.Generator;
import model.Classroom;
import utils.DateTimeConverter;

public class Processor
{
    private final String _fileName;
    private final Classroom _classroom;

    public Processor(File file){
        this._fileName = file.getName();
        this._classroom = AttendanceListManager.GenerateClassroom(file);
    }

    public void Process(Generator generator, String courseName, String begin, String End){
        this._classroom.setName( courseName );
        this._classroom.setBegin( DateTimeConverter.getLocalDateTimeFromString( begin ) );
        this._classroom.setEnd( DateTimeConverter.getLocalDateTimeFromString( End ) );
        generator.Generate( this._classroom );
    }

    public Classroom getClassroom(){
        return _classroom;
    }

    public String getFileName(){
        return _fileName;
    }
}

// =================== OLD PROCESSOR ===================
/*
    public Processor(File _file, String _start, String _stop)
    {
                this._startTime = _start;
                this._endTime = _stop;
                this._fileName = _file.getName();
                // load CSV file

                var teamsFile = new AttendanceList(_file);

                // filter to extract data for each people
                var lines = teamsFile.get_attlist();
                if (lines != null) {
                // convert lines in data structure with people & periods
                var filter = new AttendanceListAnalyzer(lines);
                // cut periods before start time and after end time
                filter.setStartAndStop(_start, _stop);
                // sort
                List<Student> studentByDuration = new ArrayList<Student>(filter.get_peopleList().values());
                Collections.sort( studentByDuration );
                // init the people collection
                this._allpeople = studentByDuration;//filter.get_peopleList().values();
            }
        }
 */
