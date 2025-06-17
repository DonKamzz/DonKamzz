/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.kamogeloleburupart3;

/**
 *
 * @author RC_Student_lab
 */

import javax.swing.JOptionPane;

public class  KamogeloLeburuPART3 {

    public static void main(String[] args) {
        // Initialize required objects
        Login login = new Login();
        Message messageHandler = new Message();
        messageHandler.loadMessagesFromJSON("messages.json");

        // ======================
        // REGISTRATION SECTION
        // ======================
        registerUser(login);

        // ======================
        // LOGIN SECTION
        // ======================
        boolean loginSuccess = loginUser(login);

        // ======================
        // MAIN APPLICATION (if login successful)
        // ======================
        if (loginSuccess) {
            runChatApplication(messageHandler);
        }
    }

    /**
     * Handles the user registration process
     * @param login The Login object to use for registration
     */
    private static void registerUser(Login login) {
        String username, password, cellPhoneNumber, firstName, lastName;

        // Username input with validation
        while (true) {
            username = JOptionPane.showInputDialog("Enter your username:");
            if (login.checkUsername(username)) {
                JOptionPane.showMessageDialog(null, "Username successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Username is not correctly formatted.\n" +
                    "It must contain an underscore and be no more than 5 characters.");
            }
        }

        // Password input with validation
        while (true) {
            password = JOptionPane.showInputDialog("Enter your password:");
            if (login.checkPasswordComplexity(password)) {
                JOptionPane.showMessageDialog(null, "Password successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Password is not correctly formatted.\n" +
                    "It must be at least 8 characters, include uppercase, number, and special character.");
            }
        }

        // Cellphone number input with validation
        while (true) {
            cellPhoneNumber = JOptionPane.showInputDialog("Enter your SA cellphone number (e.g. +27831234567):");
            if (login.checkCellPhoneNumber(cellPhoneNumber)) {
                JOptionPane.showMessageDialog(null, "Cellphone number successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Cellphone number is not correctly formatted.");
            }
        }

        // Basic info collection
        firstName = JOptionPane.showInputDialog("Enter your first name:");
        lastName = JOptionPane.showInputDialog("Enter your last name:");

        // Final registration
        JOptionPane.showMessageDialog(null, 
            login.registerUser(username, password, cellPhoneNumber, firstName, lastName));
    }

    /**
     * Handles the user login process
     * @param login The Login object to use for authentication
     * @return true if login successful, false otherwise
     */
    private static boolean loginUser(Login login) {
        String loginUsername, loginPassword;
        
        // Username input with validation
        while (true) {
            loginUsername = JOptionPane.showInputDialog("Enter username:");
            if (login.checkUsernameLoop(loginUsername)) break;
        }

        // Password input with validation
        while (true) {
            loginPassword = JOptionPane.showInputDialog("Enter password:");
            if (login.checkPasswordLoop(loginPassword)) break;
        }

        // Attempt login and show status
        boolean success = login.loginUser(loginUsername, loginPassword);
        JOptionPane.showMessageDialog(null, login.returnLoginStatus(success));
        
        return success;
    }

    /**
     * Runs the main chat application menu
     * @param messageHandler The Message object to handle message operations
     */
    private static void runChatApplication(Message messageHandler) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        boolean running = true;

        while (running) {
            // Main menu options
            String option = """
                    1) Send Messages
                    2) Show Sent Messages
                    3) Message Features
                    4) Quit
                    """;
            String choice = JOptionPane.showInputDialog(option);
            if (choice == null) continue;  // Handle cancel button

            switch (choice) {
                case "1" -> handleSendMessages(messageHandler);
                case "2" -> JOptionPane.showMessageDialog(null, messageHandler.printMessages());
                case "3" -> handleMessageFeatures(messageHandler);
                case "4" -> {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    running = false;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Choose 1-4.");
            }
        }
    }

    /**
     * Handles the message sending functionality
     * @param messageHandler The Message object to handle message operations
     */
    private static void handleSendMessages(Message messageHandler) {
        // Get number of messages to send
        int numMessages = 0;
        while (true) {
            String input = JOptionPane.showInputDialog("How many messages would you like to send?");
            if (input != null && input.matches("\\d+")) {
                numMessages = Integer.parseInt(input);
                if (numMessages > 0) break;
            }
            JOptionPane.showMessageDialog(null, "Please enter a valid number greater than 0.");
        }

        // Send each message
        for (int i = 0; i < numMessages; i++) {
            String recipient = JOptionPane.showInputDialog("Enter recipient number (e.g. +27831234567):");
            String msg = JOptionPane.showInputDialog("Enter message:");
            String result = messageHandler.sentMessage(recipient, msg);
            JOptionPane.showMessageDialog(null, result);
        }
        
        JOptionPane.showMessageDialog(null, 
            "Total messages sent/stored: " + messageHandler.returnTotalMessages());
    }

    /**
     * Handles the message features submenu
     * @param messageHandler The Message object to handle message operations
     */
    private static void handleMessageFeatures(Message messageHandler) {
        String featureMenu = """
                a) Show sender and recipients
                b) Longest sent message
                c) Search message by ID
                d) Search messages by recipient
                e) Delete message by hash
                f) Show full report
                """;
        String fChoice = JOptionPane.showInputDialog(featureMenu);
        
        if (fChoice == null) return;  // Handle cancel button
        
        switch (fChoice.toLowerCase()) {
            case "a" -> JOptionPane.showMessageDialog(null, messageHandler.showSendersAndRecipients());
            case "b" -> JOptionPane.showMessageDialog(null, messageHandler.findLongestMessage());
            case "c" -> {
                String id = JOptionPane.showInputDialog("Enter message ID to search:");
                JOptionPane.showMessageDialog(null, messageHandler.searchByID(id));
            }
            case "d" -> {
                String r = JOptionPane.showInputDialog("Enter recipient to search:");
                JOptionPane.showMessageDialog(null, messageHandler.searchByRecipient(r));
            }
            case "e" -> {
                String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                JOptionPane.showMessageDialog(null, messageHandler.deleteByHash(hash));
            }
            case "f" -> JOptionPane.showMessageDialog(null, messageHandler.fullReport());
            default -> JOptionPane.showMessageDialog(null, "Invalid option.");
        }
    }
}
