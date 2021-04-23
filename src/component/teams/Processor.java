package component.teams;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import model.Student;

public class Processor {

    private Collection<Student> _allpeople = null;
    private final String _fileName;
    private final String _startTime;
    private final String _endTime;
    private LocalTime _firstTime;
    private LocalTime _lastTime;

    public Processor( File _file, String _start, String _stop) {
        /*
         csv file to read
         start time of the course
         end time of the cource
        */
        this._startTime = _start;
        this._endTime = _stop;

        // load CSV file
        this._fileName = _file.getName();
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

    public Collection<Student> get_allpeople() {
        return _allpeople;
    }

}
