import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jpmorgan.model.Show;
import com.jpmorgan.service.BookingManager;
import com.jpmorgan.user.Admin;
import com.jpmorgan.user.Buyer;

public class BookingAppTest {
    private BookingManager bookingManager;
    private Admin admin;
    private Buyer buyer;

    @Test
    public void testAdminSetupShow() {
	    bookingManager = new BookingManager();
	    admin = new Admin();
	    buyer = new Buyer();

		int showNumber = 1;
		int numRows = 5;
		int seatsPerRow = 10;
		int cancellationWindowMinutes = 2;

        admin.setupShow(bookingManager, showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
        Show show = new Show(showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
        bookingManager.addShow(show);
        
        Assertions.assertNotNull(show);
        Assertions.assertEquals(showNumber, show.getShowNumber());
        Assertions.assertEquals(cancellationWindowMinutes, show.getCancellationWindowMinutes());
    }

    @Test
    public void testAdminViewShowDetails() {
	    bookingManager = new BookingManager();
	    admin = new Admin();
	    buyer = new Buyer();
	    
	    int showNumber = 1;
		int numRows = 5;
		int seatsPerRow = 10;
		int cancellationWindowMinutes = 2;
		String buyerPhone = "92345911";
		List<String> seatNumbers = new ArrayList<>();
		seatNumbers.add("A1");
		seatNumbers.add("A2");

        admin.setupShow(bookingManager, showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
        Show show = new Show(showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
        bookingManager.addShow(show);
	    
        //Simulate buyer booking ticket
        boolean booked = bookingManager.bookTicket(showNumber, buyerPhone, seatNumbers);
        Assertions.assertTrue(booked);
	    
    }

    @Test
    public void testBuyerCheckSeatAvailability() {
    	bookingManager = new BookingManager();
	    admin = new Admin();
	    buyer = new Buyer();
	    
	    int showNumber = 1;
		int numRows = 2;
		int seatsPerRow = 2;
		int cancellationWindowMinutes = 2;
		
        admin.setupShow(bookingManager, showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
        Show show = new Show(showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
        bookingManager.addShow(show);

        buyer.checkSeatAvailability(bookingManager, showNumber);
        Show bookedShow = bookingManager.findShowByNumber(showNumber);
        List<String> availableSeats = bookedShow.getAvailableSeats();
        Assertions.assertFalse(availableSeats.isEmpty());
        
    }

    @Test
    public void testBuyerCancelTicket() {
	    bookingManager = new BookingManager();
	    admin = new Admin();
	    buyer = new Buyer();
	    
	    int showNumber = 1;
		int numRows = 5;
		int seatsPerRow = 10;
		int cancellationWindowMinutes = 2;
		int ticketNumber = 1;
		String buyerPhone = "92345911";
		List<String> seatNumbers = new ArrayList<>();
		seatNumbers.add("A1");
		seatNumbers.add("A2");

        admin.setupShow(bookingManager, showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
        Show show = new Show(showNumber, numRows, seatsPerRow, cancellationWindowMinutes);
        bookingManager.addShow(show);

        buyer.cancelTicket(bookingManager, showNumber, ticketNumber, buyerPhone, show.getCancellationWindowMinutes());
        boolean cancelled = bookingManager.cancelTicket(showNumber, ticketNumber, buyerPhone, cancellationWindowMinutes);
        Assertions.assertFalse(cancelled);
    }
}
