package com.jpmorgan.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.jpmorgan.model.Show;
import com.jpmorgan.model.Ticket;

public class BookingManager {
    private List<Show> shows;
    private Map<String, Ticket> ticketMap;
    private Map<String, Long> ticketTimestamps; // Map to track ticket timestamps

    public BookingManager() {
        shows = new ArrayList<>();
        ticketMap = new HashMap<>();
        ticketTimestamps = new HashMap<>();
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public List<Show> getShows() {
        return shows;
    }

    public Show findShowByNumber(int showNumber) {
        for (Show show : shows) {
            if (show.getShowNumber() == showNumber) {
                return show;
            }
        }
        return null;
    }

    // This method is used to compare and order the seat numbers A1, A2, B1, B2, etc
    // This is as after the seat number is booked, but ticket cancelled, it needs to go back to the correct order.
    // Without this, it will just be placed at the back of the seat number list.
    public class SeatComparator implements Comparator<String> {
        @Override
        public int compare(String seat1, String seat2) {

            char row1 = seat1.charAt(0);
            char row2 = seat2.charAt(0);

            int seatNumber1 = Integer.parseInt(seat1.substring(1));
            int seatNumber2 = Integer.parseInt(seat2.substring(1));

            if (row1 != row2) {
                return Character.compare(row1, row2);
            } else {
                return Integer.compare(seatNumber1, seatNumber2);
            }
        }
    }

    // Booking of tickets for the particular show
    public boolean bookTicket(int showNumber, String buyerPhone, List<String> seatNumbers) {
        Show show = findShowByNumber(showNumber);
        if (show == null) {
            return false; // Show not found
        }

        List<String> availableSeats = show.getAvailableSeats();
        if (!availableSeats.containsAll(seatNumbers)) {
            return false; // Some seats are not available
        }

        // Check if the same phone number has already booked a ticket for this show
        if (hasBuyerAlreadyBooked(showNumber, buyerPhone)) {
            return false; // Same buyer has already booked a ticket for this show
        }

        int ticketNumber = generateTicketNumber();
        Ticket ticket = new Ticket(ticketNumber, buyerPhone, seatNumbers, showNumber);
        show.addTicket(ticket);

        ticketMap.put(getTicketKey(showNumber, ticketNumber), ticket);

        System.out.println("Buyerphone: " + buyerPhone);
        System.out.println("Ticket No: " + ticketNumber);

        for (String seatNumber : seatNumbers) {
            availableSeats.remove(seatNumber);
        }

        // Store the timestamp when the ticket was booked
        // This is so that we can set up the cancellation period
        long timestamp = System.currentTimeMillis();
        ticketTimestamps.put(getTicketKey(showNumber, ticketNumber), timestamp);

        scheduleCancellation(showNumber, ticketNumber, buyerPhone, show.getCancellationWindowMinutes());

        return true;
    }

    // Cancel the ticket of a particular show based on ticket number
    public boolean cancelTicket(int showNumber, int ticketNumber, String buyerPhone, int cancellationWindowMinutes) {
        String ticketKey = getTicketKey(showNumber, ticketNumber);
        if (ticketMap.containsKey(ticketKey)) {
            // Check if the ticket is within the cancellation window
            long timestamp = ticketTimestamps.get(ticketKey);
            long currentTime = System.currentTimeMillis();
            long allowedCancellationTime = timestamp + TimeUnit.MINUTES.toMillis(cancellationWindowMinutes);

            // Check if current time passed after booking ticket is still within cancellation period
            if (currentTime <= allowedCancellationTime) {
                Ticket ticket = ticketMap.get(ticketKey);

                if (ticket.getBuyerPhone().equals(buyerPhone)) {
                    for (String seatNumber : ticket.getSeatNumbers()) {
                        Show show = findShowByNumber(showNumber);
                        show.getAvailableSeats().add(seatNumber);
                    }
                    // Sort the available seats
                    Show show = findShowByNumber(showNumber);
                    List<String> availableSeats = show.getAvailableSeats();
                    Collections.sort(availableSeats, new SeatComparator()); // Sort the seats by the correct order e.g. A1,A2,B1,B2..

                    // Remove the ticket since it has been cancelled.
                    ticketMap.remove(ticketKey); 
                    ticketTimestamps.remove(ticketKey);
                    return true;
                }
            }
        }
        return false; //Ticket can no longer be cancelled due to cancellation period
    }

    // Ticket number will simply be their order of creation, starting from 1
    private int generateTicketNumber() {
        return ticketMap.size() + 1;
    }

    // Initiate the cancellation period after ticket is booked
    private void scheduleCancellation(int showNumber, int ticketNumber, String buyerPhone, int cancellationWindowMinutes) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cancelTicket(showNumber, ticketNumber, buyerPhone, cancellationWindowMinutes);
            }
        }, TimeUnit.MINUTES.toMillis(cancellationWindowMinutes));
    }

    private String getTicketKey(int showNumber, int ticketNumber) {
        return showNumber + "-" + ticketNumber;
    }

    public Ticket getTicket(int showNumber, int ticketNumber) {
        String ticketKey = getTicketKey(showNumber, ticketNumber);
        return ticketMap.get(ticketKey);
    }

    // Helper method to check if the same buyer has already booked a ticket for the same show
    private boolean hasBuyerAlreadyBooked(int showNumber, String buyerPhone) {
        for (Ticket ticket : ticketMap.values()) {
            if (ticket.getShowNumber() == showNumber && ticket.getBuyerPhone().equals(buyerPhone)) {
                return true;
            }
        }
        return false;
    }

}