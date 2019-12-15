package com.naz.PlexDownloader.util;

import java.util.Collection;
import java.util.Iterator;

public class CollectionUtil {

    /**
     * Retrieve the first element of a given collection
     * @param collection - Generic collection
     * @param <T>
     * @return - First Element
     */
    public static <T> T getFirstElement(Iterable<T> collection) {

        if (null != collection) {
            Iterator<T> iterator = collection.iterator();

            if (iterator.hasNext()) {
                return iterator.next();
            }
        }

        return null;
    }

    /**
     * Check if a given collection is null or empty.
     * @param collection
     * @return
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || !collection.iterator().hasNext();
    }
}
