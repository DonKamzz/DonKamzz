package za.ac.series;

import java.util.*;

/**
 * Series class contains the application logic.
 * It implements all required methods:
 * - CaptureSeries
 * - SearchSeries
 * - UpdateSeries
 * - DeleteSeries
 * - SeriesReport
 * - ExitSeriesApplication
 *
 * It also enforces validation:
 * - Age restriction must be between 2 and 18.
 * - Number of episodes must be numeric and >= 0.
 */
public class Series {
    // Store all series in memory using an ArrayList
    private final List<SeriesModel> memory = new ArrayList<>();

    // Scanner for reading input from the console
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Validates if age is a number between 2 and 18.
     */
    public boolean isValidAge(String ageStr) {
        if (ageStr == null || !ageStr.matches("\\d+")) return false;
        int age = Integer.parseInt(ageStr);
        return age >= 2 && age <= 18;
    }

    /**
     * Find a series by its ID.
     * @param id Series ID to search for
     * @return SeriesModel if found, otherwise null
     */
    public SeriesModel findById(String id) {
        return memory.stream()
                     .filter(s -> s.SeriesId.equalsIgnoreCase(id))
                     .findFirst().orElse(null);
    }

    /**
     * Capture a new series from user input.
     * Prompts the user until valid data is entered.
     */
    public void CaptureSeries() {
        System.out.print("Enter Series ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter Series Name: ");
        String name = scanner.nextLine().trim();

        // Validate age restriction
        String age;
        while (true) {
            System.out.print("Enter Age Restriction (2-18): ");
            age = scanner.nextLine().trim();
            if (isValidAge(age)) break;
            System.out.println("Invalid age restriction. Please enter a number between 2 and 18.");
        }

        // Validate number of episodes
        String episodes;
        while (true) {
            System.out.print("Enter Number of Episodes: ");
            episodes = scanner.nextLine().trim();
            if (episodes.matches("\\d+")) break;
            System.out.println("Invalid input. Episodes must be a number.");
        }

        // Save series into memory
        memory.add(new SeriesModel(id, name, age, episodes));
        System.out.println("Series details saved successfully.");
    }

    /**
     * Search for a series by ID.
     * Displays the series if found, otherwise prints an error.
     */
    public void SearchSeries() {
        System.out.print("Enter Series ID: ");
        String id = scanner.nextLine().trim();
        SeriesModel s = findById(id);
        if (s == null) {
            System.out.println("No series found with ID " + id);
        } else {
            display(s);
        }
    }

    /**
     * Update an existing series.
     * Allows user to leave fields blank to keep existing values.
     */
    public void UpdateSeries() {
        System.out.print("Enter Series ID to update: ");
        String id = scanner.nextLine().trim();
        SeriesModel s = findById(id);
        if (s == null) {
            System.out.println("Series not found.");
            return;
        }

        // Update name
        System.out.print("New Name (leave blank to keep '" + s.SeriesName + "'): ");
        String n = scanner.nextLine().trim();
        if (!n.isEmpty()) s.SeriesName = n;

        // Update age restriction
        while (true) {
            System.out.print("New Age Restriction (2-18, leave blank to keep '" + s.SeriesAge + "'): ");
            String a = scanner.nextLine().trim();
            if (a.isEmpty() || isValidAge(a)) {
                if (!a.isEmpty()) s.SeriesAge = a;
                break;
            }
            System.out.println("Invalid age restriction.");
        }

        // Update number of episodes
        while (true) {
            System.out.print("New Number of Episodes (leave blank to keep '" + s.SeriesNumberOfEpisodes + "'): ");
            String e = scanner.nextLine().trim();
            if (e.isEmpty() || e.matches("\\d+")) {
                if (!e.isEmpty()) s.SeriesNumberOfEpisodes = e;
                break;
            }
            System.out.println("Invalid number of episodes.");
        }

        System.out.println("Series updated successfully.");
    }

    /**
     * Delete a series by ID.
     * Prompts user for confirmation before deleting.
     */
    public void DeleteSeries() {
        System.out.print("Enter Series ID to delete: ");
        String id = scanner.nextLine().trim();
        SeriesModel s = findById(id);
        if (s == null) {
            System.out.println("Series not found.");
            return;
        }
        System.out.print("Are you sure you want to delete '" + s.SeriesName + "'? (Y/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            memory.remove(s);
            System.out.println("Series deleted successfully.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    /**
     * Print a report of all series stored.
     * If none exist, prints a suitable message.
     */
    public void SeriesReport() {
        System.out.println("---- TV Series Report ----");
        if (memory.isEmpty()) {
            System.out.println("No series captured.");
            return;
        }
        for (SeriesModel s : memory) display(s);
    }

    /**
     * Exit the application.
     */
    public void ExitSeriesApplication() {
        System.out.println("Exiting application. Goodbye!");
    }

    // Utility: print details of one series
    private void display(SeriesModel s) {
        System.out.println("ID: " + s.SeriesId + ", Name: " + s.SeriesName +
                ", Age Restriction: " + s.SeriesAge +
                ", Episodes: " + s.SeriesNumberOfEpisodes);
    }

    // Helper methods for JUnit tests
    public void addSeries(SeriesModel s) { memory.add(s); }
    public boolean updateById(String id, String n, String a, String e) {
        SeriesModel s = findById(id);
        if (s == null) return false;
        if (n != null) s.SeriesName = n;
        if (a != null && isValidAge(a)) s.SeriesAge = a;
        if (e != null && e.matches("\\d+")) s.SeriesNumberOfEpisodes = e;
        return true;
    }
    public boolean deleteById(String id) {
        SeriesModel s = findById(id);
        if (s == null) return false;
        memory.remove(s);
        return true;
    }
}
