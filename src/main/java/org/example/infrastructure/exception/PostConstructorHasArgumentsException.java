package org.example.infrastructure;

public class PostConstructorHasArgumentsException extends RuntimeException{
    public PostConstructorHasArgumentsException(String message)
    {
        super(message);
    }
}
