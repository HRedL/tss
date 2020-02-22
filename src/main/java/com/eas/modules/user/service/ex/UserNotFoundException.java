package com.eas.modules.user.service.ex;

/**
 * @Author KnoSmi
 * @Date 2018/12/28 - 16:21
 */
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4867369948853419812L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

}
