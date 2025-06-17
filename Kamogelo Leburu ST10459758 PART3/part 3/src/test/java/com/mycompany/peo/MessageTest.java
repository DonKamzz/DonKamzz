/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.peo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RC_Student_lab
 */
public class MessageTest {
    
   
    private Message message;

    @BeforeEach
    public void setUp() {
        message = new Message();
    }

    @AfterEach
    public void tearDown() {
        message = null;
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        assertTrue(message.checkRecipientCell("+12345678901"));
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        assertFalse(message.checkRecipientCell("1234abc"));
    }

    @Test
    public void testCheckMessageLength_Valid() {
        String msg = "This is a valid message.";
        assertTrue(message.checkMessageLength(msg));
    }

    @Test
    public void testCheckMessageLength_TooLong() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 300; i++) sb.append("a");
        assertFalse(message.checkMessageLength(sb.toString()));
    }

    @Test
    public void testCreateMessageHash() {
        String hash = message.createMessageHash("0000000001", 1, "Hello World!");
        assertNotNull(hash);
        assertTrue(hash.startsWith("00:1:HELLO"));  // First part of the hash pattern
    }

    @Test
    public void testCheckMessageID() {
        assertEquals("0000000001", message.checkMessageID(1));
    }

    @Test
    public void testReturnTotalMessagesInitiallyZero() {
        assertEquals(0, message.returnTotalMessages());
    }
    }

