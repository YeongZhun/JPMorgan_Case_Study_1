# Show-Booking-App

Case Study 1: Show Booking App

This app simulates the booking of a show, with 2 users - Admin and Buyer. This is a very basic and preliminary setup. To allow for multiple instances and other more practical features e.g. what happens if 2 user clicks on the same seat ticket, etc, I will have to setup a database, use locking mechanisms and retrieve the ticketing information from there. A full application would probably use Spring Boot to have a web application along with UI to order tickets and possibly authentication for Admin.

**Admin**:  
Admin has 2 commands/methods:
1) Set up a show
2) View details about a show

**Buyer**:  
Buyer has 3 commands/methods:
1) View availability of a show
2) Book tickets for a show
3) Cancel tickets for a show

**Constraints**:
- Assume max seats per row is 10 and max rows are 26. Example seat number A1,  H5 etc. The “Add” command for admin must ensure rows cannot be added beyond the upper limit of 26.
- After booking, User can cancel the seats within a time window of 2 minutes (configurable).   Cancellation after that is not allowed.
- Only one booking per phone# is allowed per show.

**Guide**:  
This app interacts with user on the terminal. Once application starts running on the IDE of your choice, user will be asked to enter their user type (Admin/Buyer). To set up a show, type in "admin". Admin password will be 123 for now. Then we will see the available admin actions.  

**Set up a show (Example)**
1) *Press 1 to set up a show.*
2) Enter show number (Integer only). *Let 1 be the show number.*
3) Enter number of rows (0 <= x < 26 and Integer only). *Let 5 be the number of rows.*
4) Enter number of seats per row: (0 <= x < 10 and Integer only). *Let 5 be number of seats per row.*
5) Default cancellation period is 2 minutes. Do you want to change that? (Y/N): *Type N*
   
Show is now set up. Now let us switch roles to Buyer to book a ticket. Press 3 to go back to user type selection. Type in "buyer".  


**Check seat availability (Example)**
1) *Press 1 to check seat availability for a show*
2) Enter show number: *Let 1 be the show number*

You should be able to see: Available Seats for Show 1: [A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, C1, C2, C3, C4, C5, D1, D2, D3, D4, D5, E1, E2, E3, E4, E5]  


**Book a ticket (Example)**
1) *Press 2 to book a ticket*
2) Enter show number: *Let 1 be the show number*
3) Enter seat numbers (comma-separated): *Let us book A1,A2,A3*
4) Enter your phone number (8 digits): *Let 12345678 be your phone number*  

You should see the following:
*Enter your phone number (8 digits): 12345678
Buyerphone: 12345678
Ticket No: 1
Ticket booked successfully. Please cancel ticket in 2 minutes or cancellations will not be allowed.*


**Cancel a ticket (Example)**
1) *Press 3 to cancel a ticket*
2) Enter show number: *Let 1 be the show number*
3) Enter ticket number: *Let 1 be the ticket number*

You should see the following IF you cancelled within cancellation period (2 minutes default):
*Ticket cancelled successfully.*  
Otherwise, you should see the following: 
*Failed to cancel the ticket. Please check if ticket number, show number and/or seat number exists (e.g. "A1"). If 2 minutes have already passed after booking, no cancellations are allowed.*  


**Confirm your ticket has been cancelled (Example)**  
Let us confirm that the ticket has been cancelled. First, the seat numbers you booked (A1,A2,A3) should be back in the seat availability.  
1) *Press 1 to check seat availability*
2) Enter show number: *Let 1 be the show number*

You should be able to see: Available Seats for Show 1: [A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, C1, C2, C3, C4, C5, D1, D2, D3, D4, D5, E1, E2, E3, E4, E5], showing that it has been added back.  


**Confirm your ticket has been booked (Example)**  
Let us assume you did not cancel the ticket. Let's confirm you have booked the ticket. Firstly, the seat availability of the show number should not have the seats you booked any more. Secondly, when Admin views show details, it should display ticket info.  

For buyer actions:
1) *Press 1 to check seat availability*
2) Enter show number: *Let 1 be the show number*

You should be able to see: Available Seats for Show 1: [A4, A5, B1, B2, B3, B4, B5, C1, C2, C3, C4, C5, D1, D2, D3, D4, D5, E1, E2, E3, E4, E5], showing that A1,A2,A3 has been booked by you.

For admin actions:
1) If you are at buyer's actions, press 4 to return to user type selection. Then type in "admin", and 123 as password.
2) *Press 2 to view show details*
3) Enter show number to view: *Enter 1*

You should be able to see the following:  
Show Number: 1  
Ticket#: 1  
Buyer Phone#: 12345678  
Seat Numbers allocated to the buyer: [A1, A2, A3]  



This concludes my guide, do let me know if anything is unclear. Thank you and feel free to leave any suggestion!  
-Yeong Zhun

