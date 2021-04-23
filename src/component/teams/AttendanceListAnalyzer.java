package component.teams;

import java.time.LocalDateTime;
import java.util.*;
import model.Student;
import utils.DateTimeConverter;

public class AttendanceListAnalyzer {

    private final LinkedList<String> _attlist;
    private HashMap<String, Student> _peopleList;

    public AttendanceListAnalyzer( LinkedList<String> _attlist) {
        this._attlist = _attlist;
        this.processList();
    }

    private void processList() {
        if (this._attlist != null) {
            this._peopleList = new HashMap<>();
            Iterator<String> element = this._attlist.iterator();
            // first line unused
            element.next();
            // process all lines
            while (element.hasNext()) {
                String input = element.next();

                String[] infos = input.split("\t");
                if (infos.length==3) {
                    String identite = infos[0];
                    LocalDateTime instant = DateTimeConverter.getLocalDateTimeFromString(infos[2]);

                    // TODO Ingore datetime that begin or end before or after the end of the course
                    Student person;
                    if (this._peopleList.containsKey(identite))
                        person = this._peopleList.get(identite);
                    else {
                        person = new Student( identite );
                        this._peopleList.put(identite, person);
                    }

                    person.addPeriod(instant);
                }
            }
        }
    }

    public HashMap<String, Student> get_peopleList() {
        return _peopleList;
    }

}
