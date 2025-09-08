package za.ac.campus;

/**
 * Base class Room.
 * Demonstrates information hiding (private fields)
 * and constructors with parameters.
 */
public class Room {
    // Private fields (information hiding)
    private String roomId;
    private int capacity;
    private boolean hasProjector;

    // Constructor
    public Room(String roomId, int capacity, boolean hasProjector) {
        this.roomId = roomId;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
    }

    // Getters and setters
    public String getRoomId() { return roomId; }
    public int getCapacity() { return capacity; }
    public boolean hasProjector() { return hasProjector; }

    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setHasProjector(boolean hasProjector) { this.hasProjector = hasProjector; }

    // Summary of this room
    public String summary() {
        return String.format("Room %s | Capacity=%d | Projector=%s",
                roomId, capacity, hasProjector ? "Yes" : "No");
    }
}
