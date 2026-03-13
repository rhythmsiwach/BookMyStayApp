/**
 * BookMyStayApp - UC5
 *
 * Booking Request Handling
 *
 * Author: YourName
 * Version: 1.4
 */

import java.util.LinkedList;
import java.util.Queue;

// ---------------------- Reservation Class ----------------------
class Reservation {

    private String guestName;
    private String roomType;
    private int numRooms;

    public Reservation(String guestName, String roomType, int numRooms) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.numRooms = numRooms;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public int getNumRooms() { return numRooms; }

    public void displayRequest() {
        System.out.println("Guest: " + guestName + ", Room Type: " + roomType + ", Requested: " + numRooms);
    }
}

// ---------------------- Booking Request Queue ----------------------
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add a reservation request to the queue
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }

    // Peek at the first request without removing
    public Reservation peekRequest() {
        return requestQueue.peek();
    }

    // Process requests (simulate processing, optional)
    public Reservation pollRequest() {
        return requestQueue.poll();
    }

    // Display all queued requests
    public void displayAllRequests() {
        System.out.println("\n--- Booking Requests in Queue ---");
        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
        System.out.println("--------------------------------\n");
    }
}

// ---------------------- Main Application for UC5 ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App - UC5: Booking Request ===");

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Sample booking requests
        Reservation r1 = new Reservation("Alice", "Single Room", 1);
        Reservation r2 = new Reservation("Bob", "Suite Room", 1);
        Reservation r3 = new Reservation("Charlie", "Double Room", 2);

        // Add requests to queue (arrival order preserved)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued requests
        bookingQueue.displayAllRequests();

        System.out.println("Booking requests collected successfully (no inventory changes).");
    }
}