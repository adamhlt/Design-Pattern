package component.sort;

import model.Classroom;
import model.Student;

import java.util.Comparator;

/**
 * Sort students by their identity
 *
 * @version 1.0
 */
public class SorterByIdentity implements Sorter
{
    /**
     * Sort students by their identity
     *
     * @param classroom A classroom to sort
     */
    @Override
    public void sortStudent(Classroom classroom)
    {
        classroom.getStudents().sort(Comparator.comparing(Student::getIdentity));
    }
}
