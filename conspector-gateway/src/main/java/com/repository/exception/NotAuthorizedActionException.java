package com.repository.exception;

/**
 * Created by aautushk on 9/5/2015.
 */
public class NotAuthorizedActionException  extends RuntimeException {

    public NotAuthorizedActionException(String message) {
        super(message);
    }
}