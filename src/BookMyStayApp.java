/**
 * BookMyStayApp - UC3
 *
 * Centralized Room Inventory Management
 *
 * Author: YourName
 * Version: 1.0
 */

import java.util.HashMap;
import java.util.Map;

// Abstract Room Class
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

    // Getters
    public String getRoomType() { return roomType; }
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }

    // Display room details
    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqm");
        System.out.println("Price: $" + price);
    }
}

// Concrete Room Classes
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 20, 80); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 30, 120); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 50, 250); }
}

// Centralized Room Inventory
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register a room type with initial availability
    public void registerRoom(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get current availability for a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability after booking or cancellation
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " available");
        }
        System.out.println("-------------------------------\n");
    }
}

// Main Application for UC3
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App - UC3: Centralized Inventory ===");

        // Create Room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.registerRoom(single.getRoomType(), 5);
        inventory.registerRoom(doubleRoom.getRoomType(), 3);
        inventory.registerRoom(suite.getRoomType(), 2);

        // Display room details with availability
        Room[] rooms = {single, doubleRoom, suite};
        for (Room room : rooms) {
            room.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability(room.getRoomType()));
            System.out.println("---------------------");
        }

        // Display full centralized inventory
        inventory.displayInventory();

        // Example update: simulate booking 1 single room
        inventory.updateAvailability(single.getRoomType(), -1);
        System.out.println("After booking 1 Single Room:");
        inventory.displayInventory();

        System.out.println("Thank you for using Book My Stay - UC3!");
    }
}