package com.example.restapi.exceptions;

public class ValueAlreadyExistsException extends RuntimeException{
    public ValueAlreadyExistsException(String message){
        super(message);
    }
}
