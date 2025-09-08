package za.ac.campus;

/**
 * Subclass Lab inherits from Room.
 * Adds extra field 'computers'.
 */
public class Lab extends Room {
    private int computers;

    // Constructor calls parent constructor
    public Lab(String roomId, int capacity, boolean hasProjector, int computers) {
        super(roomId, capacity, hasProjector);
        this.computers = computers;
    }

    // Getter and setter
    public int getComputers() { return computers; }
    public void setComputers(int computers) { this.computers = computers; }

    // Override summary to include computers
    @Override
    public String summary() {
        return super.summary() + " | Computers=" + computers;
    }
}
