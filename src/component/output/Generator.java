package component.output;

import model.Classroom;
import model.Setting;

/**
 * Interface to store generation method
 * Using Strategy Pattern from design patern
 *
 * @version 1.1
 */
public interface Generator {
    /**
     * Generate output from classroom datas
     *
     * @param classroom A classroom already processed
     */
    void Generate( Classroom classroom , Setting setting );
}
