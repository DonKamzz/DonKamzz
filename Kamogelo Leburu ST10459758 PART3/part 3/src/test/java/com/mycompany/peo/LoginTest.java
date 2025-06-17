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
public class LoginTest {
    
    public LoginTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of checkUsername method - valid case.
     */
    @Test
    public void testCheckUsername_Valid() {
        System.out.println("checkUsername - valid");
        String username = "kyl_1"; // contains underscore and ≤ 5 chars
        Login instance = new Login();
        boolean result = instance.checkUsername(username);
        assertTrue(result);
    }

    /**
     * Test of checkUsername method - invalid case.
     */
    @Test
    public void testCheckUsername_Invalid() {
        System.out.println("checkUsername - invalid");
        String username = "kyle!!!!!!!"; // too long and no underscore
        Login instance = new Login();
        boolean result = instance.checkUsername(username);
        assertFalse(result);
    }

    /**
     * Test of checkPasswordComplexity method - valid case.
     */
    @Test
    public void testCheckPasswordComplexity_Valid() {
        System.out.println("checkPasswordComplexity - valid");
        String password = "Ch&&sec@ke99!";
        Login instance = new Login();
        boolean result = instance.checkPasswordComplexity(password);
        assertTrue(result);
    }

    /**
     * Test of checkPasswordComplexity method - invalid case.
     */
    @Test
    public void testCheckPasswordComplexity_Invalid() {
        System.out.println("checkPasswordComplexity - invalid");
        String password = "password"; // no caps, number or special character
        Login instance = new Login();
        boolean result = instance.checkPasswordComplexity(password);
        assertFalse(result);
    }

    /**
     * Test of checkCellPhoneNumber - valid case.
     */
    @Test
    public void testCheckCellPhoneNumber_Valid() {
        System.out.println("checkCellPhoneNumber - valid");
        String number = "+27838968976";
        Login instance = new Login();
        boolean result = instance.checkCellPhoneNumber(number);
        assertTrue(result);
    }

    /**
     * Test of checkCellPhoneNumber - invalid case.
     */
    @Test
    public void testCheckCellPhoneNumber_Invalid() {
        System.out.println("checkCellPhoneNumber - invalid");
        String number = "0838968976"; // missing +27
        Login instance = new Login();
        boolean result = instance.checkCellPhoneNumber(number);
        assertFalse(result);
    }

    /**
     * Test of registerUser method - valid input.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        String username = "kyl_1";
        String password = "Ch&&sec@ke99!";
        String cell = "+27838968976";
        String first = "John";
        String last = "Doe";
        Login instance = new Login();
        String result = instance.registerUser(username, password, cell, first, last);
        String expResult = "Welcome John, Doe it is great to see you.";
        assertEquals(expResult, result);
    }

    /**
     * Test of returnLoginStatus method.
     */
    @Test
    public void testReturnLoginStatus_Success() {
        System.out.println("returnLoginStatus - success");
        boolean loginSuccess = true;
        Login instance = new Login();
        String result = instance.returnLoginStatus(loginSuccess);
        String expResult = "Welcome John, Doe it is great to see you.";
        assertEquals(expResult, result);
    }

    @Test
    public void testReturnLoginStatus_Failure() {
        System.out.println("returnLoginStatus - failure");
        boolean loginSuccess = false;
        Login instance = new Login();
        String result = instance.returnLoginStatus(loginSuccess);
        String expResult = "Username or password incorrect, please try again.";
        assertEquals(expResult, result);
    }
}