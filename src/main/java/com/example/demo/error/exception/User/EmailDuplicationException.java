package com.example.demo.error.exception.User;

import com.example.demo.error.ErrorCode;
import com.example.demo.error.exception.BusinessException;

public class EmailDuplicationException extends BusinessException {
    private ErrorCode errorCode;

    public EmailDuplicationException(String message, ErrorCode errorCode) {
        super(message,errorCode);
        this.errorCode = errorCode;
    }
    public EmailDuplicationException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

}
