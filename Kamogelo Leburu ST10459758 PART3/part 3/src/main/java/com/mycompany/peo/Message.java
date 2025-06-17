package com.mycompany.peo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import javax.swing.*;

public class Message {
    private final MessageRecord[] sentMessages = new MessageRecord[100];
    private final MessageRecord[] disregardedMessages = new MessageRecord[100];
    private final MessageRecord[] storedMessages = new MessageRecord[100];
    private final String[] messageHashes = new String[100];
    private final String[] messageIDs = new String[100];

    private int sentCount = 0;
    private int disregardedCount = 0;
    private int storedCount = 0;
    private int hashCount = 0;
    private int idCount = 0;

    private static final String JSON_FILENAME = "messages.json";

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

    public String checkMessageID(int index) {
        return String.format("%010d", index);
    }

    public boolean checkRecipientCell(String number) {
        return number != null && number.matches("\\+\\d{10,15}");
    }

    public boolean checkMessageLength(String message) {
        return message != null && message.length() <= 250;
    }

    public String createMessageHash(String messageID, int number, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        return messageID.substring(0, 2) + ":" + number + ":" + (firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(String recipient, String message) {
        String sender = JOptionPane.showInputDialog("Enter sender name:");

        if (!checkRecipientCell(recipient)) {
            return "Invalid recipient number.";
        }

        if (!checkMessageLength(message)) {
            JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
            return "Message too long.";
        }

        if (sentCount >= sentMessages.length) {
            JOptionPane.showMessageDialog(null, "Message limit reached.");
            return "Message limit reached.";
        }

        String id = checkMessageID(sentCount);
        String[] options = {"Send", "Store", "Disregard"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose what to do with your message:",
                "Send Message",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        String hash = createMessageHash(id, sentCount, message);
        MessageRecord newMessage = new MessageRecord(id, sender, recipient, message, hash);

        switch (choice) {
            case 0 -> {
                sentMessages[sentCount++] = newMessage;
                messageHashes[hashCount++] = hash;
                messageIDs[idCount++] = id;
                saveMessagesToJSON(JSON_FILENAME);
                return "Message sent.";
            }
            case 1 -> {
                storedMessages[storedCount++] = newMessage;
                messageHashes[hashCount++] = hash;
                messageIDs[idCount++] = id;
                saveMessagesToJSON(JSON_FILENAME);
                return "Message stored.";
            }
            case 2 -> {
                disregardedMessages[disregardedCount++] = newMessage;
                return "Message disregarded.";
            }
            default -> {
                return "No action taken.";
            }
        }
    }

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

    public int returnTotalMessages() {
        return sentCount + storedCount;
    }

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

    public String showSendersAndRecipients() {
        StringBuilder sb = new StringBuilder("Senders and Recipients:\n");
        for (int i = 0; i < sentCount; i++) {
            sb.append("Message ").append(i + 1)
              .append(" - From: ").append(sentMessages[i].sender)
              .append(" To: ").append(sentMessages[i].recipient)
              .append("\n");
        }
        return sb.toString();
    }

    public String findLongestMessage() {
        int maxLength = 0;
        MessageRecord longest = null;
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].message.length() > maxLength) {
                maxLength = sentMessages[i].message.length();
                longest = sentMessages[i];
            }
        }
        return longest != null ? "Longest Message: " + longest.message : "No messages found.";
    }

    public String searchByID(String id) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].messageID.equals(id)) {
                return "Sender: " + sentMessages[i].sender +
                       "\nRecipient: " + sentMessages[i].recipient +
                       "\nMessage: " + sentMessages[i].message;
            }
        }
        return "Message ID not found.";
    }

    public String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder("Messages to " + recipient + ":\n");
        boolean found = false;
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].recipient.equals(recipient)) {
                found = true;
                sb.append("- ").append(sentMessages[i].message).append(" (From: ").append(sentMessages[i].sender).append(")\n");
            }
        }
        return found ? sb.toString() : "No messages to that recipient.";
    }

    public String deleteByHash(String hash) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].messageHash.equals(hash)) {
                for (int j = i; j < sentCount - 1; j++) {
                    sentMessages[j] = sentMessages[j + 1];
                }
                sentMessages[--sentCount] = null;
                return "Message deleted.";
            }
        }
        return "Message hash not found.";
    }

    public String fullReport() {
        StringBuilder report = new StringBuilder("Full Message Report:\n");
        for (int i = 0; i < sentCount; i++) {
            MessageRecord m = sentMessages[i];
            report.append("ID: ").append(m.messageID)
                  .append(" | Hash: ").append(m.messageHash)
                  .append(" | Sender: ").append(m.sender)
                  .append(" | Recipient: ").append(m.recipient)
                  .append(" | Message: ").append(m.message).append("\n");
        }
        return report.toString();
    }
}
