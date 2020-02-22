package com.eas.modules.user.service.ex;

/**
 * @Author KnoSmi
 * @Date 2018/12/17 - 1:36
 */
public class PasswordNotMatchException extends RuntimeException {

    private static final long serialVersionUID = 4237883975285430748L;

    public PasswordNotMatchException() {
        super();
    }

    public PasswordNotMatchException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotMatchException(String message) {
        super(message);
    }

    public PasswordNotMatchException(Throwable cause) {
        super(cause);

    }
}
