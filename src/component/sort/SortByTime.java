package component.sort;

import model.Classroom;
import model.Student;

import java.util.Comparator;

public class SortByTime implements Sort
{
    @Override
    public void sortStudent(Classroom classroom) { classroom.getStudents().sort(Comparator.comparing(Student::getTotalAttendanceDuration)); }
}
