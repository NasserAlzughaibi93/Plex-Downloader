package com.naz.PlexDownloader.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilTest {

    private Collection<Integer> collection;

    @BeforeEach
    void setUp() {
        collection = new ArrayDeque<>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetFirstElement() {
        collection.add(1);
        collection.add(2);

        assertEquals(1, CollectionUtil.getFirstElement(collection));
    }

    @Test
    void testIsNullOrEmpty() {

        collection.add(1);

        assertFalse(CollectionUtil.isNullOrEmpty(collection));

        collection = null;

        assertTrue(CollectionUtil.isNullOrEmpty(collection));

    }
}