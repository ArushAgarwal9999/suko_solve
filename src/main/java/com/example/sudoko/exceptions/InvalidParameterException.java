package com.example.sudoko.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InvalidParameterException extends RuntimeException{
    String msg;
    public InvalidParameterException(String msg)
    {
        super(msg);
        this.msg = msg;
    }
}
