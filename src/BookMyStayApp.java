/**
 * BookMyStayApp - UC7
 *
 * Add-On Service Selection
 *
 * Author: YourName
 * Version: 1.6
 */

import java.util.*;

// ---------------------- Add-On Service Class ----------------------
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }

    public void displayService() {
        System.out.println(serviceName + " ($" + price + ")");
    }
}

// ---------------------- Add-On Service Manager ----------------------
class AddOnServiceManager {

    // Map of reservationID -> list of selected services
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add a service to a reservation
    public void addService(String reservationId, AddOnService service) {
        List<AddOnService> services = reservationServices.computeIfAbsent(reservationId, k -> new ArrayList<>());
        services.add(service);
        System.out.println("Added service " + service.getServiceName() + " to reservation " + reservationId);
    }

    // Get total cost of services for a reservation
    public double calculateTotal(String reservationId) {
        double total = 0;
        List<AddOnService> services = reservationServices.get(reservationId);
        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }
        return total;
    }

    // Display all services for a reservation
    public void displayServices(String reservationId) {
        List<AddOnService> services = reservationServices.get(reservationId);
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected for reservation " + reservationId);
            return;
        }
        System.out.println("Add-On Services for reservation " + reservationId + ":");
        for (AddOnService s : services) {
            s.displayService();
        }
        System.out.println("Total Additional Cost: $" + calculateTotal(reservationId));
    }
}

// ---------------------- Main Application for UC7 ----------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay App - UC7: Add-On Service Selection ===");

        // Example reservation IDs (these would come from UC6 allocations)
        String reservationId1 = "SIN100";
        String reservationId2 = "SUI101";

        // Initialize add-on service manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Define some sample services
        AddOnService breakfast = new AddOnService("Breakfast", 15);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 30);
        AddOnService spa = new AddOnService("Spa Access", 50);

        // Attach services to reservations
        serviceManager.addService(reservationId1, breakfast);
        serviceManager.addService(reservationId1, spa);
        serviceManager.addService(reservationId2, airportPickup);

        // Display services and total costs
        serviceManager.displayServices(reservationId1);
        serviceManager.displayServices(reservationId2);

        System.out.println("Add-on service selection completed successfully.");
    }
}
