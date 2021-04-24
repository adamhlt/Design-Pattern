package component.sort;

import model.Classroom;
import model.Student;

import java.util.Comparator;

/**
 * Sort students by their time being connected
 *
 * @version 1.0
 */
public class SortByTime implements Sort {
    @Override
    public void sortStudent( Classroom classroom ) { classroom.getStudents().sort( Comparator.comparing( Student::getTotalAttendanceDuration ) ); }
}
