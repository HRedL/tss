package com.eas.modules.user.service.ex;

/**
 * @Author KnoSmi
 * @Date 2018/12/17 - 1:38
 */
public class UsernameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7327544464478764319L;

    public UsernameNotFoundException() {
        super();
    }

    public UsernameNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UsernameNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }

    public UsernameNotFoundException(Throwable cause) {
        super(cause);
    }
}
