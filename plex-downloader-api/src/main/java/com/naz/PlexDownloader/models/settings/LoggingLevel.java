package com.naz.PlexDownloader.models.settings;


public enum LoggingLevel {

    INFO("Info"),

    WARN("Warn"),

    DEBUG("Debug"),

    TRACE("Trace"),

    ERROR("Error");

    private String loggingLevel;

    LoggingLevel(String logginLevel) {
        this.loggingLevel = logginLevel;
    }

    public String getLoggingLevel() {
        return loggingLevel;
    }
}
