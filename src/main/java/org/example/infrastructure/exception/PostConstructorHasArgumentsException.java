package org.example.infrastructure.exception;

public class PostConstructorHasArgumentsException extends RuntimeException{
    public PostConstructorHasArgumentsException(String message)
    {
        super(message);
    }
}
