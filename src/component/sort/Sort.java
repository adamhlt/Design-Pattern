package component.sort;

import model.Classroom;

/**
 * Interface to store sort method
 * Using Strategy Pattern from design patern
 *
 * @version 1.0
 */
public interface Sort {
    /**
     * Sort all students of a classroom
     *
     * @param classroom A classroom to sort
     */
    void sortStudent( Classroom classroom );
}
