package com.jpmorgan.main;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.jpmorgan.model.Show;
import com.jpmorgan.model.Ticket;
import com.jpmorgan.service.BookingManager;
import com.jpmorgan.user.Admin;
import com.jpmorgan.user.Buyer;

public class BookingApp {
    public static void main(String[] args) {
        BookingManager bookingManager = new BookingManager();

        // Define the admin password
        String adminPassword = "123";

        Admin admin = new Admin();
        Buyer buyer = new Buyer();

        // Interactive loop for user actions
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Enter user type (admin/buyer): ");
                String userType = scanner.nextLine();

                // Admin actions
                if ("admin".equalsIgnoreCase(userType)) {
                    System.out.print("Enter admin password: ");
                    String password = scanner.nextLine();
                    if (password.equals(adminPassword)) {
                        while (true) {
                            System.out.println("\n");
                            System.out.println("Admin actions:");
                            System.out.println("1. Setup a show");
                            System.out.println("2. View a show");
                            System.out.println("3. Go back to user type selection");
                            System.out.println("4. Exit");
                            System.out.print("Enter your choice: ");

                            int adminChoice;
                            try {
                                adminChoice = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline character
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please try again and enter a valid value.");
                                scanner.nextLine(); // Consume the invalid input
                                continue; // Retry the admin choice
                            }
                            

                            switch (adminChoice) {
                                case 1:
                                    System.out.println("Enter show details... \n");
                                    int showNumber, numRows, seatsPerRow, cancellationWindowMinutes;
                                    // Parse show details and call admin.setupShow()
                                    try {

                                        System.out.println("Enter show number: ");
                                        showNumber = scanner.nextInt();
                                        System.out.println("Enter number of rows: ");
                                        numRows = scanner.nextInt();
                                        System.out.println("Enter number of seats per row: ");
                                        seatsPerRow = scanner.nextInt();

                                        System.out.println(
                                                "Default cancellation period is 2 minutes. Do you want to change that? (Y/N): ");
                                        String input = scanner.next();
                                        if ("Y".equalsIgnoreCase(input)) {
                                            System.out.println("Enter number of minutes for cancellation period: ");
                                            cancellationWindowMinutes = scanner.nextInt();
                                            admin.setupShow(bookingManager, showNumber, numRows, seatsPerRow,
                                                    cancellationWindowMinutes);
                                            break;
                                        } else if ("N".equalsIgnoreCase(input)) {
                                            cancellationWindowMinutes = 2;
                                            admin.setupShow(bookingManager, showNumber, numRows, seatsPerRow,
                                                    cancellationWindowMinutes);
                                            break;
                                        } else {
                                            System.out.println("Please input Y or N only and try again.");
                                            scanner.nextLine(); // Consume the invalid input
                                            continue; // Retry entering show details
                                        }

                                    } catch (InputMismatchException e) {
                                        System.out.println(
                                                "Invalid input. Please try again and enter valid integer values to set up a show.");
                                        scanner.nextLine(); // Consume the invalid input
                                        continue; // Retry entering show details
                                    }

                                case 2:
                                    System.out.print("Enter show number to view: \n");
                                    int showNumberToView;
                                    try {
                                        showNumberToView = scanner.nextInt();
                                        scanner.nextLine(); // Consume the newline character

                                    } catch (InputMismatchException e) {
                                        System.out.println(
                                                "Invalid input. Please try again and enter valid integer values for show number.");
                                        scanner.nextLine(); // Consume the invalid input
                                        continue; // Retry entering show details
                                    }
                                    admin.viewShowDetails(bookingManager, showNumberToView);
                                    break;
                                case 3:
                                    System.out.println("Exiting admin mode amd going back to user type selection. \n");
                                    break;
                                case 4:
                                    System.out.println("Exiting the program. \n");
                                    return;
                                default:
                                    System.out.println("Invalid admin choice. Try again. \n");
                            }

                            if (adminChoice == 3) {
                                break; // Exit admin mode
                            }
                        }
                    } else {
                        System.out.println("Access denied. Invalid password. \n");
                    }
                // Buyer Actions
                } else if ("buyer".equalsIgnoreCase(userType)) {
                    while (true) {
                        System.out.println("\n");
                        System.out.println("Buyer actions:");
                        System.out.println("1. Check seat availability");
                        System.out.println("2. Book a ticket");
                        System.out.println("3. Cancel a ticket");
                        System.out.println("4. Go back to user type selection");
                        System.out.println("5. Exit");
                        System.out.print("Enter your choice: ");

                        int buyerChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (buyerChoice) {
                            case 1:
                                System.out.print("Enter show number to check availability: \n");
                                int showNumber;
                                try {
                                    showNumber = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline character
                                } catch (InputMismatchException e) {
                                    System.out.println(
                                            "Invalid input. Please try again and enter valid integer values for show number.");
                                    scanner.nextLine(); // Consume the invalid input
                                    continue; // Retry entering show details
                                }
                                buyer.checkSeatAvailability(bookingManager, showNumber);
                                break;
                            case 2:
                                System.out.print("Enter show number: \n");
                                int showNumberToBook;
                                try {
                                    showNumberToBook = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline character
                                    if (bookingManager.findShowByNumber(showNumberToBook) != null) {
                                        System.out.print("Enter seat numbers (comma-separated): ");
                                        String seatInput = scanner.nextLine();
                                        List<String> seatNumbers = Arrays.asList(seatInput.split(","));
                                        buyer.bookTicket(bookingManager, showNumberToBook, seatNumbers, scanner);
                                    } else {
                                        System.out.println("Show number does not exist. \n");
                                    }
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println(
                                            "Invalid input. Please try again and enter valid integer values for show number.");
                                    scanner.nextLine(); // Consume the invalid input
                                    continue; // Retry entering show details
                                }
                            case 3:
                                System.out.print("Enter show number: \n");
                                int showNumberToCancel;
                                try {
                                    showNumberToCancel = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline character
                                    Show showToCancel = bookingManager.findShowByNumber(showNumberToCancel);

                                    if (showToCancel != null) {
                                        System.out.print("Enter ticket number to cancel: ");
                                        int ticketNumber = scanner.nextInt();
                                        scanner.nextLine(); // Consume the newline character
                            
                                        // Retrieve the ticket based on show and ticket number
                                        Ticket ticketToCancel = bookingManager.getTicket(showNumberToCancel, ticketNumber);
                            
                                        if (ticketToCancel != null) {
                                            // Retrieve the buyerPhone from the ticket
                                            String buyerPhone = ticketToCancel.getBuyerPhone();
                            
                                            // Pass buyerPhone to the cancelTicket method
                                            buyer.cancelTicket(bookingManager, showNumberToCancel, ticketNumber, buyerPhone, showToCancel.getCancellationWindowMinutes());
                                        } else {
                                            System.out.println("Ticket number does not exist for the given show. Or the cancellation period might have passed. \n");
                                        }
                                    } else {
                                        System.out.println("Show number does not exist. \n");
                                    }
                                    break;

                                } catch (InputMismatchException e) {
                                    System.out.println(
                                            "Invalid input. Please try again and enter valid integer values for show number.");
                                    scanner.nextLine(); // Consume the invalid input
                                    continue; // Retry entering show details
                                }

                            case 4:
                                System.out.println("Exiting buyer mode and going back to user type selection. \n");
                                break;
                            case 5:
                                System.out.println("Exiting the program. \n");
                                return;
                            default:
                                System.out.println("Invalid buyer choice. Try again. \n");
                        }

                        if (buyerChoice == 4) {
                            break; // Go back to user type selection
                        }
                    }
                } else {
                    System.out.println("Invalid user type. Try again (admin/buyer). \n");
                }
            }
        } catch (InputMismatchException e) {
            // Handle input mismatch exception
            System.out.println("Invalid input. Please try again and enter a valid value.");
        } catch (NoSuchElementException e) {
            // Handle scanner-related exception
            System.out.println("An error occurred while reading input.");
        } catch (Exception e) {
            // Handle other unexpected exceptions
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }

        finally {
            // Close the scanner when done
            scanner.close();
        }
    }
}
