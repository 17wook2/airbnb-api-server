package com.example.demo.error.exception.User;

import com.example.demo.error.ErrorCode;
import com.example.demo.error.exception.BusinessException;

public class PasswordNotMatchException extends BusinessException {
    private ErrorCode errorCode;

    public PasswordNotMatchException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public PasswordNotMatchException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
