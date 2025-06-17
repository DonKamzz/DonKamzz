/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kamogeloleburupart3;

import javax.swing.JOptionPane;

/**
 * Handles user authentication including registration and login processes.
 * Provides validation for usernames, passwords, and cellphone numbers.
 */
public class Login {
    // User registration fields
    private String registeredUsername;
    private String registeredPassword;
    private String registeredFirstName;
    private String registeredLastName;

    // ======================
    // VALIDATION METHODS
    // ======================

    /**
     * Validates a username against requirements.
     * @param username The username to validate
     * @return true if username contains underscore and is ≤5 characters, false otherwise
     */
    public boolean checkUsername(String username) {
        return username != null && 
               username.contains("_") && 
               username.length() <= 5;
    }

    /**
     * Validates password complexity requirements.
     * @param password The password to validate
     * @return true if password meets all complexity rules:
     *         - At least 8 characters
     *         - Contains uppercase letter
     *         - Contains digit
     *         - Contains special character
     */
    public boolean checkPasswordComplexity(String password) {
        return password != null &&
               password.length() >= 8 &&
               !password.equals(password.toLowerCase()) && // Has uppercase
               password.matches(".*\\d.*") && // Has digit
               password.matches(".*[^a-zA-Z0-9].*"); // Has special char
    }

    /**
     * Validates South African cellphone number format.
     * @param number The phone number to validate
     * @return true if matches +27 followed by 9 digits (e.g. +27831234567)
     */
    public boolean checkCellPhoneNumber(String number) {
        return number != null && 
               number.matches("^\\+27\\d{9}$");
    }

    // ======================
    // REGISTRATION METHODS
    // ======================

    /**
     * Registers a new user with all required information.
     * @param username The user's username
     * @param password The user's password
     * @param cell The user's cellphone number
     * @param first The user's first name
     * @param last The user's last name
     * @return Registration confirmation message with user details
     */
    public String registerUser(String username, String password, String cell, String first, String last) {
        // Store registration details
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredFirstName = first;
        this.registeredLastName = last;

        return String.format(
            "Registration successful!\n" +
            "Username: %s\n" +
            "Password: %s\n" +
            "Cell: %s\n" +
            "Name: %s %s",
            username, maskPassword(password), cell, first, last
        );
    }

    /**
     * Masks a password for display (shows first 2 chars followed by ***)
     * @param password The password to mask
     * @return Masked password string
     */
    private String maskPassword(String password) {
        if (password == null || password.length() <= 2) {
            return "***";
        }
        return password.substring(0, 2) + "***";
    }

    // ======================
    // LOGIN METHODS
    // ======================

    /**
     * Authenticates a user.
     * @param inputUsername The username to check
     * @param inputPassword The password to check
     * @return true if credentials match registered user, false otherwise
     */
    public boolean loginUser(String inputUsername, String inputPassword) {
        return inputUsername != null &&
               inputPassword != null &&
               inputUsername.equals(registeredUsername) && 
               inputPassword.equals(registeredPassword);
    }

    /**
     * Continuously checks username against registered username.
     * @param inputUsername The username to verify
     * @return true if username matches, false otherwise (shows error message)
     */
    public boolean checkUsernameLoop(String inputUsername) {
        if (inputUsername != null && inputUsername.equals(registeredUsername)) {
            return true;
        }
        JOptionPane.showMessageDialog(null, 
            "Incorrect username. Try again.", 
            "Login Error", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    }

    /**
     * Continuously checks password against registered password.
     * @param inputPassword The password to verify
     * @return true if password matches, false otherwise (shows error message)
     */
    public boolean checkPasswordLoop(String inputPassword) {
        if (inputPassword != null && inputPassword.equals(registeredPassword)) {
            return true;
        }
        JOptionPane.showMessageDialog(null, 
            "Incorrect password. Try again.", 
            "Login Error", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    }

    /**
     * Generates appropriate login status message.
     * @param loginSuccess Whether login was successful
     * @return Welcome message if successful, failure message otherwise
     */
    public String returnLoginStatus(boolean loginSuccess) {
        return loginSuccess ? 
            String.format("Welcome, %s %s!", registeredFirstName, registeredLastName) :
            "Login failed. Please try again.";
    }
}