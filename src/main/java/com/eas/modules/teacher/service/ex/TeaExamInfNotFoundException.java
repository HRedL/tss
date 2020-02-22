package com.eas.modules.teacher.service.ex;


public class TeaExamInfNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6054450446448619038L;

    public TeaExamInfNotFoundException() {
    }

    public TeaExamInfNotFoundException(String message) {
        super(message);
    }

    public TeaExamInfNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeaExamInfNotFoundException(Throwable cause) {
        super(cause);
    }

    public TeaExamInfNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
