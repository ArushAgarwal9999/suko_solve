package com.example.sudoko.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.EXPECTATION_FAILED)
public class InvalidInputValueException extends RuntimeException {
    String msg;
    public InvalidInputValueException(String msg)
    {
        super(msg);
        this.msg = msg;
    }
}
