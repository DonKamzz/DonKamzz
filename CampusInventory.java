package za.ac.campus;

/**
 * Inventory class uses an array to store Room/Lab objects.
 * Demonstrates loops, searching, updating, and reporting.
 */
public class CampusInventory {
    private Room[] items;  // Fixed-size array
    private int count = 0; // Number of stored items

    // Constructor
    public CampusInventory(int size) {
        items = new Room[size];
    }

    /**
     * Add a room/lab into the inventory.
     * Prevents duplicates.
     */
    public boolean add(Room r) {
        if (count >= items.length) return false; // no space
        if (findById(r.getRoomId()) != null) return false; // duplicate
        items[count++] = r;
        return true;
    }

    /**
     * Find room/lab by ID using loop.
     */
    public Room findById(String id) {
        for (int i = 0; i < count; i++) {
            if (items[i].getRoomId().equalsIgnoreCase(id)) return items[i];
        }
        return null;
    }

    /**
     * Update capacity of a room.
     */
    public boolean updateCapacity(String id, int newCap) {
        Room r = findById(id);
        if (r == null) return false;
        r.setCapacity(newCap);
        return true;
    }

    /**
     * Delete a room/lab by ID.
     * Uses loop to shift array elements.
     */
    public boolean delete(String id) {
        for (int i = 0; i < count; i++) {
            if (items[i].getRoomId().equalsIgnoreCase(id)) {
                // shift left
                for (int j = i; j < count - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[count - 1] = null;
                count--;
                return true;
            }
        }
        return false;
    }

    /**
     * Generate a report of all stored rooms/labs.
     * Uses loop to display each.
     */
    public String report() {
        if (count == 0) return "No rooms captured.";
        StringBuilder sb = new StringBuilder("=== Campus Inventory Report ===\n");
        int totalSeats = 0, totalLabs = 0, totalRooms = 0, totalPCs = 0;

        for (int i = 0; i < count; i++) {
            sb.append(items[i].summary()).append("\n");
            totalSeats += items[i].getCapacity();
            if (items[i] instanceof Lab) {
                totalLabs++;
                totalPCs += ((Lab) items[i]).getComputers();
            } else {
                totalRooms++;
            }
        }

        sb.append("Rooms=").append(totalRooms)
          .append(", Labs=").append(totalLabs)
          .append(", Total Seats=").append(totalSeats)
          .append(", Total PCs=").append(totalPCs);
        return sb.toString();
    }

    // Getter
    public int size() { return count; }
}
