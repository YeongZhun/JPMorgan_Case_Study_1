package com.jpmorgan.model;

import java.util.List;

public class Ticket {
    private int ticketNumber;
    private String buyerPhone;
    private List<String> seatNumbers;
    private int showNumber;
    private boolean cancelled;

    public Ticket(int ticketNumber, String buyerPhone, List<String> seatNumbers, int showNumber) {
        this.ticketNumber = ticketNumber;
        this.buyerPhone = buyerPhone;
        this.seatNumbers = seatNumbers;
        this.showNumber = showNumber;
        cancelled = false;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public int getShowNumber() {
        return showNumber;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true; 
    }
}
