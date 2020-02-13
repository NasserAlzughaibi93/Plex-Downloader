package com.naz.PlexDownloader.exceptions.rest;

import com.naz.PlexDownloader.exceptions.BaseRuntimeException;

public class JwtInvalidException extends BaseRuntimeException {

    public JwtInvalidException(String messageKey, Object[] args) {
        super(messageKey, args);
    }

    public JwtInvalidException(String messageKey) {
        super(messageKey);
    }

}
