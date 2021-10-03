package com.david.exceptions;

public class ParsingException extends Exception{

    private String message;

    public ParsingException(String message){
        super(message);
    }
}
