import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ---------- Reservation Class ----------
class Reservation {
    private String reservationId;
    private String guestName;
    private LocalDate bookingDate;

    public Reservation(String reservationId, String guestName, LocalDate bookingDate) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.bookingDate = bookingDate;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public LocalDate getBookingDate() { return bookingDate; }
}

// ---------- Booking History ----------
class BookingHistory {
    private List<Reservation> confirmedBookings = new ArrayList<>();

    // Add a confirmed reservation
    public void addReservation(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    // Retrieve all reservations in insertion order
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(confirmedBookings);
    }
}

// ---------- Booking Report Service ----------
class BookingReportService {

    // Generate a summary report of all bookings
    public void generateSummaryReport(BookingHistory history) {
        List<Reservation> bookings = history.getAllReservations();
        System.out.println("=== Booking Summary Report ===");
        System.out.println("Total Bookings: " + bookings.size());

        for (Reservation res : bookings) {
            System.out.println("Reservation ID: " + res.getReservationId() +
                    ", Guest: " + res.getGuestName() +
                    ", Date: " + res.getBookingDate());
        }
    }
}

// ---------- Main Example ----------
public class BookMyStayApp {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("RES101", "Alice", LocalDate.now());
        Reservation r2 = new Reservation("RES102", "Bob", LocalDate.now().plusDays(1));
        Reservation r3 = new Reservation("RES103", "Charlie", LocalDate.now().plusDays(2));

        // Add reservations to history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin generates report
        reportService.generateSummaryReport(history);
    }
}

