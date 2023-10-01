package com.jpmorgan.model;

import java.util.ArrayList;
import java.util.List;

public class Show {
    private int showNumber;
    private int numRows;
    private int seatsPerRow;
    private int cancellationWindowMinutes;
    private List<String> availableSeats;
    private List<Ticket> tickets; 

    public Show(int showNumber, int numRows, int seatsPerRow, int cancellationWindowMinutes) {
        this.showNumber = showNumber;
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.cancellationWindowMinutes = cancellationWindowMinutes;
        initializeAvailableSeats();
        this.tickets = new ArrayList<>(); // Initialize the list of tickets
    }

    private void initializeAvailableSeats() {
        availableSeats = new ArrayList<>();
        char row = 'A';
        for (int i = 0; i < numRows; i++) {
            for (int j = 1; j <= seatsPerRow; j++) {
                availableSeats.add(String.format("%c%d", row, j));
            }
            row++;
        }
    }

    public int getShowNumber() {
        return showNumber;
    }

    public List<String> getAvailableSeats() {
        return availableSeats;
    }

    public int getCancellationWindowMinutes() {
        return cancellationWindowMinutes;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Ticket findTicketByNumber(int ticketNumber) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketNumber() == ticketNumber) {
                return ticket;
            }
        }
        return null; // Ticket not found
    }
}
