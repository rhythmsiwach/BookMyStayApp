/**
 * BookMyStayApp
 *
 * Book My Stay App
 * Implements:
 *   UC1 - Welcome Message
 *   UC2 - Basic Room Types & Static Availability
 *
 * Author: YourName
 * Version: 1.1
 */

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

// Main Application
public class BookMyStayApp {

    public static void main(String[] args) {

        // ---------------------- UC1: Welcome Message ----------------------
        System.out.println("======================================");
        System.out.println("      Welcome to Book My Stay App");
        System.out.println("            Version 1.1");
        System.out.println("======================================");
        System.out.println("Application started successfully.");
        System.out.println("Thank you for using Book My Stay!\n");

        // ---------------------- UC2: Room Types & Availability ----------------------
        System.out.println("=== Available Room Types ===\n");

        // Create Room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display room details with availability
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println("---------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("---------------------");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("---------------------");

        System.out.println("Thank you for viewing room availability!");
    }
}
