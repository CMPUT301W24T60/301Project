package com.mp012202200038_01.BasicEventManager;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventTest {

    private Event event;

    @Before
    public void setUp() {
        event = new Event("key", "title", "desc", "day", "month", "location", "time", "posterUrl", "qrCodeData", false);
    }

    @Test
    public void testGetters() {
        assertEquals("key", event.getKey());
        assertEquals("title", event.getTitle());
        assertEquals("desc", event.getDesc());
        assertEquals("day", event.getDay());
        assertEquals("month", event.getMonth());
        assertEquals("location", event.getLocation());
        assertEquals("time", event.getTime());
        assertEquals("posterUrl", event.getPosterUrl());
        assertEquals("qrCodeData", event.getQrCodeData());
        assertFalse(event.isCheckedIn());
    }

    @Test
    public void testSetters() {
        event.setKey("newKey");
        event.setTitle("newTitle");
        event.setDesc("newDesc");
        event.setDay("newDay");
        event.setMonth("newMonth");
        event.setLocation("newLocation");
        event.setTime("newTime");
        event.setPosterUrl("newPosterUrl");
        event.setQrCodeData("newQrCodeData");
        event.setIsCheckedIn(true);

        assertEquals("newKey", event.getKey());
        assertEquals("newTitle", event.getTitle());
        assertEquals("newDesc", event.getDesc());
        assertEquals("newDay", event.getDay());
        assertEquals("newMonth", event.getMonth());
        assertEquals("newLocation", event.getLocation());
        assertEquals("newTime", event.getTime());
        assertEquals("newPosterUrl", event.getPosterUrl());
        assertEquals("newQrCodeData", event.getQrCodeData());
        assertTrue(event.isCheckedIn());
    }
}
