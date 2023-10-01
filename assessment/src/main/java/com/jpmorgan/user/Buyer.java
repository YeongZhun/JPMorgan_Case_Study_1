package com.jpmorgan.user;

import java.util.List;
import java.util.Scanner;

import com.jpmorgan.model.Show;
import com.jpmorgan.model.Ticket;
import com.jpmorgan.service.BookingManager;

public class Buyer extends User {
    public Buyer() {     
    }
   
    // Check seat availability of a specific show
    public void checkSeatAvailability(BookingManager bookingManager, int showNumber) {
        Show show = bookingManager.findShowByNumber(showNumber);
        if (show != null) {
            List<String> availableSeats = show.getAvailableSeats();
            if (!availableSeats.isEmpty()) {
                System.out.println("Available Seats for Show " + show.getShowNumber() + ": " + availableSeats);
            } else {
                System.out.println("No available seats for Show " + show.getShowNumber());
            }
        } else {
            System.out.println("Show not found.");
        }
    }

    // Book ticket of a show 
    public void bookTicket(BookingManager bookingManager, int showNumber, List<String> seatNumbers, Scanner scanner) {
        // Validate input and book ticket
        // Phone number must be 8 digits exactly. Can change regex if needed.
        String phoneRegex = "\\d{8}";
        String buyerPhone;
        while (true) {
            System.out.print("\nEnter your phone number (8 digits): ");
            buyerPhone = scanner.nextLine();
            if (buyerPhone.matches(phoneRegex)) {
                break;
            } else {
                System.out.print("Phone number should be 8 digits only. \n");    
            }
        }
        boolean booked = bookingManager.bookTicket(showNumber, buyerPhone, seatNumbers);
        if (booked) {
            System.out.println("Ticket booked successfully. Please cancel ticket in " + bookingManager.findShowByNumber(showNumber).getCancellationWindowMinutes() + " minutes or cancellations will not be allowed.");
        } else {
            System.out.println("Failed to book the ticket. Please check if show number and/or seat number exists (e.g. \"A1\"), or it could have already been booked (Please check seat availability). Do note that we only allow one ticket booking per phone number for each show.");
        }
    }

    // Cancel ticket of a specific show
    public void cancelTicket(BookingManager bookingManager, int showNumber, int ticketNumber, String buyerPhone, int cancellationWindowMinutes) {
        // Validate input and cancel ticket
        // Need to check if cancellation period has passed
        boolean cancelled = bookingManager.cancelTicket(showNumber, ticketNumber, buyerPhone, cancellationWindowMinutes);
        if (cancelled) {
            Show show = bookingManager.findShowByNumber(showNumber);
            //This is to remove the ticket, 
            if (show != null) {
                Ticket ticket = show.findTicketByNumber(ticketNumber);
                if (ticket != null) {
                    ticket.cancel();
                } else {
                    System.out.println("Failed to find ticket for cancellation based on show " + showNumber);
                }
            }
            System.out.println("Ticket cancelled successfully.");
        } else {
            System.out.println("Failed to cancel the ticket. Please check if ticket number, show number and/or seat number exists (e.g. \"A1\"). If " + bookingManager.findShowByNumber(showNumber).getCancellationWindowMinutes() + " minutes have already passed after booking, no cancellations are allowed.");
        }
    }
}