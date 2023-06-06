/*
 * @file: 
 * @author: Luis Oliveira <https://github.com/LuisCarlosOliveira>
 * @date
 * @brief
 */
package myExceptions;

/**
 *
 * @author Luis Oliveira <https://github.com/LuisCarlosOliveira>
 */
public class ProjectAlreadyInEdition extends Exception {

    /**
     * Constructs an instance of <code>ProjectAlreadyInEdition</code> with the
     * specified detail message.
     *
     * @param message the detail message.
     */
    public ProjectAlreadyInEdition(String message) {
        super(message);
    }
}
