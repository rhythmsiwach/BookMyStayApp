/**
 * BookMyStayApp - UC6
 *
 * Reservation Confirmation & Room Allocation
 *
 * Author: YourName
 * Version: 1.5
 */

import java.util.*;

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

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    public boolean isEmpty() { return requestQueue.isEmpty(); }

    public Reservation pollRequest() { return requestQueue.poll(); }

    public void displayAllRequests() {
        System.out.println("\n--- Booking Requests in Queue ---");
        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
        System.out.println("--------------------------------\n");
    }
}

// ---------------------- Room Inventory ----------------------
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() { inventory = new HashMap<>(); }

    public void registerRoom(String roomType, int count) { inventory.put(roomType, count); }

    public int getAvailability(String roomType) { return inventory.getOrDefault(roomType, 0); }

    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " available");
        }
        System.out.println("-------------------------------\n");
    }
}

// ---------------------- Room Allocation Service ----------------------
class BookingService {

    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms; // roomType -> set of room IDs
    private int roomIdCounter = 100; // simple counter to generate unique room IDs

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    // Process a reservation request
    public void allocateRoom(Reservation reservation) {
        String roomType = reservation.getRoomType();
        int available = inventory.getAvailability(roomType);

        if (available >= reservation.getNumRooms()) {
            Set<String> allocatedSet = allocatedRooms.computeIfAbsent(roomType, k -> new HashSet<>());
            List<String> assignedIds = new ArrayList<>();

            for (int i = 0; i < reservation.getNumRooms(); i++) {
                String roomId;
                do {
                    roomId = roomType.substring(0, 3).toUpperCase() + (roomIdCounter++);
                } while (allocatedSet.contains(roomId));

                allocatedSet.add(roomId);
                assignedIds.add(roomId);
            }

            // Update inventory
            inventory.updateAvailability(roomType, -reservation.getNumRooms());

            // Confirm reservation
            System.out.println("Reservation Confirmed for " + reservation.getGuestName() + ":");
            System.out.println("Assigned Room IDs: " + assignedIds);
        } else {
            System.out.println("Sorry " + reservation.getGuestName() + ", not enough " + roomType + " available.");
        }
    }

    // Process all requests in the queue
    public void processQueue(BookingRequestQueue queue) {
        while (!queue.isEmpty()) {
            Reservation r = queue.pollRequest();
            allocateRoom(r);
        }
    }
}

// ---------------------- Main Application ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App - UC6: Reservation Confirmation & Allocation ===");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom("Single Room", 5);
        inventory.registerRoom("Double Room", 3);
        inventory.registerRoom("Suite Room", 2);

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Alice", "Single Room", 1));
        queue.addRequest(new Reservation("Bob", "Suite Room", 1));
        queue.addRequest(new Reservation("Charlie", "Double Room", 2));
        queue.addRequest(new Reservation("Diana", "Single Room", 2));

        // Display queued requests
        queue.displayAllRequests();

        // Process booking requests and allocate rooms
        BookingService service = new BookingService(inventory);
        service.processQueue(queue);

        // Display remaining inventory
        inventory.displayInventory();

        System.out.println("All booking requests processed.");
    }
}