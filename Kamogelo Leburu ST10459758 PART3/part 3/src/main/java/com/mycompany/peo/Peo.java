package com.mycompany.peo;

import javax.swing.JOptionPane;

public class Peo {

    public static void main(String[] args) {
        Login login = new Login();
        Message messageHandler = new Message();
        messageHandler.loadMessagesFromJSON("messages.json");

        // Registration
        String username, password, cellPhoneNumber, firstName, lastName;

        while (true) {
            username = JOptionPane.showInputDialog("Enter your username:");
            if (login.checkUsername(username)) {
                JOptionPane.showMessageDialog(null, "Username successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted.\nIt must contain an underscore and be no more than 5 characters.");
            }
        }

        while (true) {
            password = JOptionPane.showInputDialog("Enter your password:");
            if (login.checkPasswordComplexity(password)) {
                JOptionPane.showMessageDialog(null, "Password successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted.\nIt must be at least 8 characters, include uppercase, number, and special character.");
            }
        }

        while (true) {
            cellPhoneNumber = JOptionPane.showInputDialog("Enter your SA cellphone number (e.g. +27831234567):");
            if (login.checkCellPhoneNumber(cellPhoneNumber)) {
                JOptionPane.showMessageDialog(null, "Cellphone number successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Cellphone number is not correctly formatted.");
            }
        }

        firstName = JOptionPane.showInputDialog("Enter your first name:");
        lastName = JOptionPane.showInputDialog("Enter your last name:");

        JOptionPane.showMessageDialog(null, login.registerUser(username, password, cellPhoneNumber, firstName, lastName));

        // Login
        String loginUsername, loginPassword;
        while (true) {
            loginUsername = JOptionPane.showInputDialog("Enter username:");
            if (login.checkUsernameLoop(loginUsername)) break;
        }

        while (true) {
            loginPassword = JOptionPane.showInputDialog("Enter password:");
            if (login.checkPasswordLoop(loginPassword)) break;
        }

        boolean success = login.loginUser(loginUsername, loginPassword);
        JOptionPane.showMessageDialog(null, login.returnLoginStatus(success));

        if (success) {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
            boolean running = true;

            while (running) {
                String option = """
                        1) Send Messages
                        2) Show Sent Messages
                        3) Message Features
                        4) Quit
                        """;
                String choice = JOptionPane.showInputDialog(option);
                if (choice == null) continue;

                switch (choice) {
                    case "1" -> {
                        int numMessages = 0;
                        while (true) {
                            String input = JOptionPane.showInputDialog("How many messages would you like to send?");
                            if (input != null && input.matches("\\d+")) {
                                numMessages = Integer.parseInt(input);
                                if (numMessages > 0) break;
                            }
                            JOptionPane.showMessageDialog(null, "Please enter a valid number greater than 0.");
                        }

                        for (int i = 0; i < numMessages; i++) {
                            String recipient = JOptionPane.showInputDialog("Enter recipient number (e.g. +27831234567):");
                            String msg = JOptionPane.showInputDialog("Enter message:");
                            String result = messageHandler.sentMessage(recipient, msg);
                            JOptionPane.showMessageDialog(null, result);
                        }
                        JOptionPane.showMessageDialog(null, "Total messages sent/stored: " + messageHandler.returnTotalMessages());
                    }
                    case "2" -> JOptionPane.showMessageDialog(null, messageHandler.printMessages());

                    case "3" -> {
                        String featureMenu = """
                                a) Show sender and recipients
                                b) Longest sent message
                                c) Search message by ID
                                d) Search messages by recipient
                                e) Delete message by hash
                                f) Show full report
                                """;
                        String fChoice = JOptionPane.showInputDialog(featureMenu);
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
                        }
                    }

                    case "4" -> {
                        JOptionPane.showMessageDialog(null, "Goodbye!");
                        running = false;
                    }

                    default -> JOptionPane.showMessageDialog(null, "Invalid option. Choose 1-4.");
                }
            }
        }
    }
}
