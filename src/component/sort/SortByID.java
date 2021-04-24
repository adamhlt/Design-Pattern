package component.sort;

import model.Classroom;
import model.Student;

import java.util.Comparator;

/**
 * Sort students by their id
 *
 * @version 1.0
 */
public class SortByID implements Sort {
    @Override
    public void sortStudent( Classroom classroom ) {
        classroom.getStudents().sort( Comparator.comparing( Student::getId ) );
    }
}
