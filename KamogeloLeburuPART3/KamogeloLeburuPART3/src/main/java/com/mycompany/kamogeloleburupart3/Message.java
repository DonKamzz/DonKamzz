/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kamogeloleburupart3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import javax.swing.*;

/**
 * Handles all message-related operations including sending, storing,
 * searching, and managing message records.
 */
public class Message {
    // Constants
    private static final String JSON_FILENAME = "messages.json";
    private static final int MAX_MESSAGES = 100;
    private static final int MAX_MESSAGE_LENGTH = 250;

    // Message storage arrays
    private final MessageRecord[] sentMessages = new MessageRecord[MAX_MESSAGES];
    private final MessageRecord[] disregardedMessages = new MessageRecord[MAX_MESSAGES];
    private final MessageRecord[] storedMessages = new MessageRecord[MAX_MESSAGES];
    private final String[] messageHashes = new String[MAX_MESSAGES];
    private final String[] messageIDs = new String[MAX_MESSAGES];

    // Counters for each array
    private int sentCount = 0;
    private int disregardedCount = 0;
    private int storedCount = 0;
    private int hashCount = 0;
    private int idCount = 0;

    /**
     * Represents a single message record with all relevant information.
     */
    public static class MessageRecord {
        public String messageID;
        public String sender;
        public String recipient;
        public String message;
        public String messageHash;

        public MessageRecord(String messageID, String sender, String recipient, String message, String messageHash) {
            this.messageID = messageID;
            this.sender = sender;
            this.recipient = recipient;
            this.message = message;
            this.messageHash = messageHash;
        }

        public MessageRecord() {}
    }

    // ======================
    // VALIDATION METHODS
    // ======================

    /**
     * Generates a formatted message ID.
     * @param index The index of the message
     * @return Formatted 10-digit ID string
     */
    public String checkMessageID(int index) {
        return String.format("%010d", index);
    }

    /**
     * Validates a recipient's cellphone number.
     * @param number The phone number to validate
     * @return true if valid, false otherwise
     */
    public boolean checkRecipientCell(String number) {
        return number != null && number.matches("\\+\\d{10,15}");
    }

    /**
     * Checks if message length is within limits.
     * @param message The message to check
     * @return true if valid length, false otherwise
     */
    public boolean checkMessageLength(String message) {
        return message != null && message.length() <= MAX_MESSAGE_LENGTH;
    }

    // ======================
    // MESSAGE PROCESSING METHODS
    // ======================

    /**
     * Creates a unique hash for a message.
     * @param messageID The message's ID
     * @param number The message number
     * @param message The message content
     * @return Generated hash string
     */
    public String createMessageHash(String messageID, int number, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        return messageID.substring(0, 2) + ":" + number + ":" + (firstWord + lastWord).toUpperCase();
    }

    /**
     * Handles the complete message sending process.
     * @param recipient The recipient's phone number
     * @param message The message content
     * @return Status message about the operation
     */
    public String sentMessage(String recipient, String message) {
        // Validate recipient and message
        if (!checkRecipientCell(recipient)) {
            return "Invalid recipient number.";
        }

        if (!checkMessageLength(message)) {
            JOptionPane.showMessageDialog(null, 
                "Please enter a message of less than " + MAX_MESSAGE_LENGTH + " characters.");
            return "Message too long.";
        }

        if (sentCount >= sentMessages.length) {
            JOptionPane.showMessageDialog(null, "Message limit reached.");
            return "Message limit reached.";
        }

        // Get sender information
        String sender = JOptionPane.showInputDialog("Enter sender name:");
        if (sender == null || sender.trim().isEmpty()) {
            return "Sender name required.";
        }

        // Present action options to user
        String[] options = {"Send", "Store", "Disregard"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Choose what to do with your message:",
            "Send Message",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );

        // Create message record
        String id = checkMessageID(sentCount);
        String hash = createMessageHash(id, sentCount, message);
        MessageRecord newMessage = new MessageRecord(id, sender, recipient, message, hash);

        // Process user choice
        switch (choice) {
            case 0 -> {  // Send
                sentMessages[sentCount++] = newMessage;
                messageHashes[hashCount++] = hash;
                messageIDs[idCount++] = id;
                saveMessagesToJSON(JSON_FILENAME);
                return "Message sent.";
            }
            case 1 -> {  // Store
                storedMessages[storedCount++] = newMessage;
                messageHashes[hashCount++] = hash;
                messageIDs[idCount++] = id;
                saveMessagesToJSON(JSON_FILENAME);
                return "Message stored.";
            }
            case 2 -> {  // Disregard
                disregardedMessages[disregardedCount++] = newMessage;
                return "Message disregarded.";
            }
            default -> {
                return "No action taken.";
            }
        }
    }

    // ======================
    // MESSAGE DISPLAY METHODS
    // ======================

    /**
     * Generates a formatted string of all sent messages.
     * @return Formatted message list or "Coming soon" if empty
     */
    public String printMessages() {
        if (sentCount == 0) return "Coming soon.";

        StringBuilder output = new StringBuilder("Messages:\n");
        for (int i = 0; i < sentCount; i++) {
            MessageRecord message = sentMessages[i];
            output.append((i + 1)).append(") ID: ").append(message.messageID)
                  .append(" | Hash: ").append(message.messageHash)
                  .append(" | Sender: ").append(message.sender)
                  .append(" | Recipient: ").append(message.recipient)
                  .append(" | Message: ").append(message.message).append("\n");
        }
        return output.toString();
    }

    /**
     * @return Total count of sent and stored messages
     */
    public int returnTotalMessages() {
        return sentCount + storedCount;
    }

    // ======================
    // DATA PERSISTENCE METHODS
    // ======================

    /**
     * Saves stored messages to JSON file.
     * @param filename The file to save to
     */
    public void saveMessagesToJSON(String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            MessageRecord[] toSave = new MessageRecord[storedCount];
            System.arraycopy(storedMessages, 0, toSave, 0, storedCount);
            gson.toJson(toSave, writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages: " + e.getMessage());
        }
    }

    /**
     * Loads messages from JSON file.
     * @param filename The file to load from
     */
    public void loadMessagesFromJSON(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<MessageRecord[]>() {}.getType();
            MessageRecord[] loaded = gson.fromJson(reader, type);

            if (loaded != null) {
                storedCount = Math.min(loaded.length, storedMessages.length);
                System.arraycopy(loaded, 0, storedMessages, 0, storedCount);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading messages: " + e.getMessage());
        }
    }

    // ======================
    // MESSAGE QUERY METHODS
    // ======================

    /**
     * Generates a list of all senders and recipients.
     * @return Formatted string with sender-recipient pairs
     */
    public String showSendersAndRecipients() {
        if (sentCount == 0) return "No messages sent yet.";

        StringBuilder sb = new StringBuilder("Senders and Recipients:\n");
        for (int i = 0; i < sentCount; i++) {
            sb.append("Message ").append(i + 1)
              .append(" - From: ").append(sentMessages[i].sender)
              .append(" To: ").append(sentMessages[i].recipient)
              .append("\n");
        }
        return sb.toString();
    }

    /**
     * Finds the longest message in the sent messages.
     * @return The longest message or "No messages found"
     */
    public String findLongestMessage() {
        if (sentCount == 0) return "No messages found.";

        int maxLength = 0;
        MessageRecord longest = null;
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].message.length() > maxLength) {
                maxLength = sentMessages[i].message.length();
                longest = sentMessages[i];
            }
        }
        return "Longest Message (" + maxLength + " chars): " + longest.message;
    }

    /**
     * Searches for a message by its ID.
     * @param id The message ID to search for
     * @return Message details or "not found" message
     */
    public String searchByID(String id) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].messageID.equals(id)) {
                return String.format(
                    "ID: %s\nSender: %s\nRecipient: %s\nMessage: %s",
                    sentMessages[i].messageID,
                    sentMessages[i].sender,
                    sentMessages[i].recipient,
                    sentMessages[i].message
                );
            }
        }
        return "Message ID not found.";
    }

    /**
     * Searches for messages by recipient.
     * @param recipient The recipient to search for
     * @return List of messages or "not found" message
     */
    public String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder("Messages to " + recipient + ":\n");
        boolean found = false;
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].recipient.equals(recipient)) {
                found = true;
                sb.append("- ").append(sentMessages[i].message)
                  .append(" (From: ").append(sentMessages[i].sender).append(")\n");
            }
        }
        return found ? sb.toString() : "No messages to that recipient.";
    }

    /**
     * Deletes a message by its hash.
     * @param hash The hash of the message to delete
     * @return Status message about the operation
     */
    public String deleteByHash(String hash) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].messageHash.equals(hash)) {
                // Shift all subsequent messages down
                System.arraycopy(sentMessages, i + 1, sentMessages, i, sentCount - i - 1);
                sentMessages[--sentCount] = null;
                saveMessagesToJSON(JSON_FILENAME);
                return "Message deleted.";
            }
        }
        return "Message hash not found.";
    }

    /**
     * Generates a comprehensive report of all sent messages.
     * @return Formatted report string
     */
    public String fullReport() {
        if (sentCount == 0) return "No messages sent yet.";

        StringBuilder report = new StringBuilder("Full Message Report:\n");
        for (int i = 0; i < sentCount; i++) {
            MessageRecord m = sentMessages[i];
            report.append("ID: ").append(m.messageID)
                  .append(" | Hash: ").append(m.messageHash)
                  .append(" | Sender: ").append(m.sender)
                  .append(" | Recipient: ").append(m.recipient)
                  .append(" | Message: ").append(m.message).append("\n");
        }
        report.append("\nTotal Messages: ").append(sentCount);
        return report.toString();
    }
}