package com.oid.streamxbackend.common.exception;

public class VideoAccessDeniedException extends  RuntimeException{

    public VideoAccessDeniedException(String message){
        super(message);
    }
}
