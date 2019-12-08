package com.naz.PlexDownloader.util;

import java.util.Locale;

public class BaseRuntimeException extends RuntimeException {

    private static final String DEFAULT_EXCEPTION_RESOURCE_NAME = "exceptions.ExceptionMessages";

    public BaseRuntimeException() {super();}

    public BaseRuntimeException(String message) {super(message);}

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(String message, Object[] args) {
        super(I18nUtil.getLocalizedMessage(DEFAULT_EXCEPTION_RESOURCE_NAME, message, args, Locale.getDefault()));
    }

}
