package com.example.restapi.exceptions;

public class QueryNotFoundExceptions extends RuntimeException{
    public QueryNotFoundExceptions(String message){
        super(message);
    }
}
