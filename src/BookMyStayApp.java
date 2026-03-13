/**
 * BookMyStayApp - UC4
 *
 * Room Search & Availability Check
 *
 * Author: YourName
 * Version: 1.3
 */

import java.util.HashMap;
import java.util.Map;

// ---------------------- UC2: Room Classes ----------------------
abstract class Room {

    private String roomType;
    private int beds;
    private int size;
    private double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() { return roomType; }
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqm");
        System.out.println("Price: $" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 20, 80); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 30, 120); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 50, 250); }
}

// ---------------------- UC3: Room Inventory ----------------------
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void registerRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

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

// ---------------------- UC4: Room Search Service ----------------------
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Display available rooms without modifying inventory
    public void displayAvailableRooms(Room[] rooms) {
        System.out.println("\n=== Search Results: Available Rooms ===\n");

        boolean anyAvailable = false;

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + available);
                System.out.println("---------------------");
                anyAvailable = true;
            }
        }

        if (!anyAvailable) {
            System.out.println("No rooms are currently available.");
        }
    }
}

// ---------------------- Main Application ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App - UC4: Room Search ===");

        // Create rooms
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        Room[] rooms = {single, doubleRoom, suite};

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom(single.getRoomType(), 5);
        inventory.registerRoom(doubleRoom.getRoomType(), 0); // simulate no availability
        inventory.registerRoom(suite.getRoomType(), 2);

        // Perform search
        RoomSearchService searchService = new RoomSearchService(inventory);
        searchService.displayAvailableRooms(rooms);

        System.out.println("Thank you for using Book My Stay - UC4!");
    }
}