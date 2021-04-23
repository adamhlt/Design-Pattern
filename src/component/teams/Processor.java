package component.teams;

import java.io.File;

import model.Classroom;

public class Processor
{
    public Processor(File _file, Classroom classroom)
    {
        //TODO Process file parsing and fill classroom
    }
}

// =================== OLD PROCESSOR ===================
/*
public Processor(File _file, String _start, String _stop)
    {
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
 */
