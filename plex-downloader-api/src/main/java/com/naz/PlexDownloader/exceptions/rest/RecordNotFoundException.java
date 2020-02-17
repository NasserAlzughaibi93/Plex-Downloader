package com.naz.PlexDownloader.exceptions.rest;

import com.naz.PlexDownloader.exceptions.BaseRuntimeException;

public class RecordNotFoundException extends BaseRuntimeException {

    public RecordNotFoundException(String messageKey, Object[] args) {
        super(messageKey, args);
    }

    public RecordNotFoundException(String messageKey) {
        super(messageKey);
    }
}
