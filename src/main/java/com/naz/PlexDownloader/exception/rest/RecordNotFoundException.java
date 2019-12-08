package com.naz.PlexDownloader.exception.rest;

import com.naz.PlexDownloader.util.BaseRuntimeException;

public class RecordNotFoundException extends BaseRuntimeException {

    public RecordNotFoundException(String messageKey, Object[] args) {
        super(messageKey, args);
    }
}
