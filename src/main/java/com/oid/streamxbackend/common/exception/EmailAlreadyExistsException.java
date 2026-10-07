package com.oid.streamxbackend.common.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String
                                       message){
        super(message);
    }
}
