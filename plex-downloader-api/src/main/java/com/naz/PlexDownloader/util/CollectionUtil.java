package com.naz.PlexDownloader.util;

import com.naz.PlexDownloader.models.plex.Video;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

    /**
     * Create a quick list
     * @param objects
     * @param <T>
     * @return
     */
    public static <T> List<T> createList(Object...objects) {

        List theList = new ArrayList();

        for (Object object :
                objects) {
            theList.add(object);
        }

        return theList;
    }

    /**
     * Quickly build a collection
     * @param objects
     * @param <T>
     * @return
     */
    public static <T> Collection<T> createCollection(Object...objects) {

        Collection theCollection = new ArrayList();

        for (Object object :
                objects) {
            theCollection.add(object);
        }

        return theCollection;
    }

}
