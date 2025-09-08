package za.ac.campus;

import java.util.Scanner;

/**
 * Main class provides a menu-driven console app.
 * Demonstrates use of loops and user interaction.
 */
public class CampusMain {
    private static final Scanner sc = new Scanner(System.in);
    private static final CampusInventory inventory = new CampusInventory(50);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addRoom(); break;
                case "2": addLab(); break;
                case "3": search(); break;
                case "4": updateCapacity(); break;
                case "5": delete(); break;
                case "6": System.out.println(inventory.report()); break;
                case "7": running = false; System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("===== Campus Asset Tracker =====");
        System.out.println("1. Add Room");
        System.out.println("2. Add Lab");
        System.out.println("3. Search by ID");
        System.out.println("4. Update Capacity");
        System.out.println("5. Delete");
        System.out.println("6. Report");
        System.out.println("7. Exit");
        System.out.print("Choose option: ");
    }

    private static void addRoom() {
        System.out.print("Room ID: ");
        String id = sc.nextLine();
        System.out.print("Capacity: ");
        int cap = Integer.parseInt(sc.nextLine());
        System.out.print("Has projector (Y/N): ");
        boolean proj = sc.nextLine().equalsIgnoreCase("Y");
        Room r = new Room(id, cap, proj);
        System.out.println(inventory.add(r) ? "Room added." : "Failed to add.");
    }

    private static void addLab() {
        System.out.print("Lab ID: ");
        String id = sc.nextLine();
        System.out.print("Capacity: ");
        int cap = Integer.parseInt(sc.nextLine());
        System.out.print("Has projector (Y/N): ");
        boolean proj = sc.nextLine().equalsIgnoreCase("Y");
        System.out.print("Computers: ");
        int pcs = Integer.parseInt(sc.nextLine());
        Lab l = new Lab(id, cap, proj, pcs);
        System.out.println(inventory.add(l) ? "Lab added." : "Failed to add.");
    }

    private static void search() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        Room r = inventory.findById(id);
        System.out.println(r == null ? "Not found." : r.summary());
    }

    private static void updateCapacity() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("New capacity: ");
        int cap = Integer.parseInt(sc.nextLine());
        System.out.println(inventory.updateCapacity(id, cap) ? "Updated." : "Not found.");
    }

    private static void delete() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.println(inventory.delete(id) ? "Deleted." : "Not found.");
    }
}
