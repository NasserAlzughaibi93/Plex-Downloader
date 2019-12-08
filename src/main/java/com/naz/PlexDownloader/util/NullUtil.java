package com.naz.PlexDownloader.util;

public class NullUtil {


    public static boolean isNullOrEmpty(Object object) {
        return object == null || object.toString().isEmpty();
    }

}
