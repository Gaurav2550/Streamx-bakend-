package com.oid.streamxbackend.common.exception;

public class CategoryAlreadyExistsException extends  RuntimeException{

    public CategoryAlreadyExistsException(String name) {
        super("Category  Already exists : " + name );
    }
}
