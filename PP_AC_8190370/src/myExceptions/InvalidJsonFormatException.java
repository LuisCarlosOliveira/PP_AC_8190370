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
public class InvalidJsonFormatException extends Exception{

    /**
     * Constructs an instance of <code>InvalidJsonFormatException</code> with
     * the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidJsonFormatException(String message) {
        super(message);
    }
}
