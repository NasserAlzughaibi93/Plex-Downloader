package com.naz.PlexDownloader.exceptions;

public class RestException extends BaseRuntimeException {

    public RestException(Long id) {
        super("Could not find ID " + id);
    }

    public RestException(String messageKey, Object[] args) {
        super(messageKey, args);
    }

}
