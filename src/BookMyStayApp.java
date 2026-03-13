import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

// ---------- Reservation Class ----------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomId;
    private LocalDate bookingDate;

    public Reservation(String reservationId, String guestName, String roomId, LocalDate bookingDate) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomId = roomId;
        this.bookingDate = bookingDate;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomId() { return roomId; }
    public LocalDate getBookingDate() { return bookingDate; }
}

// ---------- Inventory Management ----------
class InventoryManager {
    private Map<String, Integer> roomInventory = new HashMap<>();

    public void addRoomType(String roomType, int count) {
        roomInventory.put(roomType, count);
    }

    public void incrementRoom(String roomType) {
        roomInventory.put(roomType, roomInventory.getOrDefault(roomType, 0) + 1);
    }

    public boolean allocateRoom(String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public int getAvailableRooms(String roomType) {
        return roomInventory.getOrDefault(roomType, 0);
    }
}

// ---------- Booking History ----------
class BookingHistory {
    private List<Reservation> confirmedBookings = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    public boolean removeReservation(String reservationId) {
        return confirmedBookings.removeIf(r -> r.getReservationId().equals(reservationId));
    }

    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(confirmedBookings);
    }

    public boolean reservationExists(String reservationId) {
        return confirmedBookings.stream()
                .anyMatch(r -> r.getReservationId().equals(reservationId));
    }
}

// ---------- Cancellation Service ----------
class CancellationService {
    private BookingHistory bookingHistory;
    private InventoryManager inventoryManager;
    private Stack<String> rollbackRoomStack = new Stack<>();

    public CancellationService(BookingHistory history, InventoryManager inventory) {
        this.bookingHistory = history;
        this.inventoryManager = inventory;
    }

    public boolean cancelReservation(Reservation reservation, String roomType) {
        String reservationId = reservation.getReservationId();

        if (!bookingHistory.reservationExists(reservationId)) {
            System.out.println("Cancellation failed: Reservation does not exist.");
            return false;
        }

        rollbackRoomStack.push(reservation.getRoomId());
        inventoryManager.incrementRoom(roomType);
        bookingHistory.removeReservation(reservationId);

        System.out.println("Reservation " + reservationId + " cancelled successfully.");
        return true;
    }

    public String rollbackLastRoom() {
        if (!rollbackRoomStack.isEmpty()) {
            return rollbackRoomStack.pop();
        }
        return null;
    }
}

// ---------- Main Example ----------
public class BookMyStayApp {
    public static void main(String[] args) {
        // Initialize inventory
        InventoryManager inventory = new InventoryManager();
        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Double", 3);

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Create reservations
        Reservation r1 = new Reservation("RES101", "Alice", "S101", LocalDate.now());
        Reservation r2 = new Reservation("RES102", "Bob", "S102", LocalDate.now().plusDays(1));

        // Allocate inventory
        inventory.allocateRoom("Single");
        inventory.allocateRoom("Single");

        // Add reservations to history
        history.addReservation(r1);
        history.addReservation(r2);

        // Initialize cancellation service
        CancellationService cancellationService = new CancellationService(history, inventory);

        // Cancel a booking
        cancellationService.cancelReservation(r1, "Single");

        // Remaining bookings
        System.out.println("\nRemaining Bookings:");
        history.getAllReservations().forEach(res ->
                System.out.println(res.getReservationId() + " - " + res.getGuestName())
        );

        // Inventory after cancellation
        System.out.println("\nAvailable Single Rooms: " + inventory.getAvailableRooms("Single"));

        // Rollback last released room
        String rolledBackRoom = cancellationService.rollbackLastRoom();
        System.out.println("\nRolled back room from stack: " + rolledBackRoom);
    }
}