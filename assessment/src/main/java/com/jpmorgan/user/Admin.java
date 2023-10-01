package com.jpmorgan.user;

import java.util.List;

import com.jpmorgan.model.Show;
import com.jpmorgan.model.Ticket;
import com.jpmorgan.service.BookingManager;

public class Admin extends User {

    public Admin() {
        

    }

    // Overloading method. If no cancellationWindowMinutes given, default is 2 mins. 
    // Not needed at the moment, but no harm leaving it here just in case, since it is the default.
    public void setupShow(BookingManager bookingManager, int showNumber, int numRows, int seatsPerRow) {
        setupShow(bookingManager, showNumber, numRows, seatsPerRow, 2); 
    }

    // Set up show
    public void setupShow(BookingManager bookingManager, int showNumber, int numRows, int seatsPerRow,
            int cancellationWindowMinutes) {
        if (numRows <= 0 | numRows > 26) {
            System.out.println("Number of rows for show must be at least 1 and cannot exceed 26!");
        } else if (seatsPerRow <= 0 | seatsPerRow > 10) {
            System.out.println("Number of seats per row must be at least 1 and cannot exceed 10!");
        } else {
            Show show = new Show(showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
            bookingManager.addShow(show);
            System.out.println("Show has been set up!");
        }
    }

    // View show details. If no buyer have booked tickets yet, it will only show the show number
    public void viewShowDetails(BookingManager bookingManager, int showNumber) {
        Show show = bookingManager.findShowByNumber(showNumber);
        if (show != null) {
            System.out.println("Show Number: " + show.getShowNumber());

            List<Ticket> tickets = show.getTickets();

            for (Ticket ticket : tickets) {
                if (!ticket.isCancelled()) {

                
                System.out.println("Ticket#: " + ticket.getTicketNumber());
                System.out.println("Buyer Phone#: " + ticket.getBuyerPhone());
                System.out.println("Seat Numbers allocated to the buyer: " + ticket.getSeatNumbers());
                System.out.println();
                }
            }
        } else {
            System.out.println("Show not found.");
        }
    }

}
