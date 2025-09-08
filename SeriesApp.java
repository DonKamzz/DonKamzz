package za.ac.series;

import java.util.Scanner;

/**
 * Console application entry point.
 */
public class SeriesApp {
    public static void main(String[] args) {
        Series series = new Series();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("===== TV Series Management =====");
            System.out.println("1. Capture a new TV series");
            System.out.println("2. Search for a TV series");
            System.out.println("3. Update a TV series");
            System.out.println("4. Delete a TV series");
            System.out.println("5. View series report");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            String option = sc.nextLine().trim();
            switch (option) {
                case "1": series.CaptureSeries(); break;
                case "2": series.SearchSeries(); break;
                case "3": series.UpdateSeries(); break;
                case "4": series.DeleteSeries(); break;
                case "5": series.SeriesReport(); break;
                case "6": series.ExitSeriesApplication(); return;
                default: System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }
}
