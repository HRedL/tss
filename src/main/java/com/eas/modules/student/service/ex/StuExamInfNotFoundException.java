package com.eas.modules.student.service.ex;

/**
 * @Author KnoSmi
 * @Date 2018/12/18 - 16:28
 */
public class StuExamInfNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6054450446448619038L;

    public StuExamInfNotFoundException() {
    }

    public StuExamInfNotFoundException(String message) {
        super(message);
    }

    public StuExamInfNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StuExamInfNotFoundException(Throwable cause) {
        super(cause);
    }

    public StuExamInfNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
