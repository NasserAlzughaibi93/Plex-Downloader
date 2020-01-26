package com.naz.PlexDownloader.util;

import com.naz.PlexDownloader.exceptions.BaseRuntimeException;

import java.util.Collection;

public class ValidationUtil {

    /*public static void NotNullOrEmpty(Object object, String message) {

        if (object == null) {
            throw new BaseRuntimeException(message);
        } else if (object instanceof String) {
            if (((String) object).isEmpty()) {
                throw new BaseRuntimeException(message);
            }
        }
    }*/

    public static void NotNullOrEmpty(String message, Object... objects) {

        for (Object object : objects) {
            if (object == null) {
                throw new BaseRuntimeException(message);
            } else if (object instanceof String && ((String) object).isEmpty()) {
                throw new BaseRuntimeException(message);
            } else if (object instanceof Collection && CollectionUtil.isNullOrEmpty((Collection<?>) object)) {
                throw new BaseRuntimeException(message);
            }
        }
    }

    public static void NotNullOrEmpty(String message, Object[] args, Object... objects) {

        for (Object object : objects) {
            if (object == null) {
                throw new BaseRuntimeException(message, args);
            } else if (object instanceof String && ((String) object).isEmpty()) {
                throw new BaseRuntimeException(message, args);
            } else if (object instanceof Collection && CollectionUtil.isNullOrEmpty((Collection<?>) object)) {
                throw new BaseRuntimeException(message, args);
            }
        }
    }
}
