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
public class TaskCreationException extends Exception{

    /**
     * Constructs an instance of <code>TaskCreationException</code> with the
     * specified detail message.
     *
     * @param message the detail message.
     */
    public TaskCreationException(String message) {
        super(message);
    }
}
