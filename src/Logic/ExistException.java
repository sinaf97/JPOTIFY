package Logic;

import java.io.Serializable;

/**
 * an exception thrown when somthing already exists
 */

public class ExistException extends Exception implements Serializable {

    public ExistException(){
        super();
    }
}
