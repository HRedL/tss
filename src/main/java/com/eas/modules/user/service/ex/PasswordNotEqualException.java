package com.eas.modules.user.service.ex;

/**
 * @Author KnoSmi
 * @Date 2019/7/2 - 20:29
 */
public class PasswordNotEqualException extends RuntimeException {

    public PasswordNotEqualException() {
        super();
    }

    public PasswordNotEqualException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PasswordNotEqualException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotEqualException(String message) {
        super(message);
    }

    public PasswordNotEqualException(Throwable cause) {
        super(cause);

    }
}
