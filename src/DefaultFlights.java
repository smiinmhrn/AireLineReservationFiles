import java.io.IOException;

// this class is responsible for adding 10 default airlines
public class DefaultFlights {
//    private final NewingClasses NEWING_CLASSES;
    private Flights flights;
//    public DefaultFlights(NewingClasses newClasses) {
//        this.NEWING_CLASSES = newClasses;
//    }
    public DefaultFlights(Flights flights) {
        this.flights = flights;
    }
    public void add() throws IOException {
        if (flights.randomAccessFile.length() == 0){
            addDefault();
        }
    }
    public void addDefault() throws IOException {
        flights.write(
                new Flight("FlightId", "Origin", "Destination", "Date",
                        "Time", "Price", "Seats")
        );
        flights.write(
                new Flight("WX-12", "Yazd", "Tehran", "1401-12-10",
                        "12:30", "700000", "51")
        );
        flights.write(
                new Flight("WX-15", "Mashhad", "Ahvaz", "1401-12-11",
                        "08:00" , "900000" , "245")
        );
        flights.write(
                new Flight("WX-22", "Mashhad", "Shiraz", "1401-10-11",
                        "08:00", "500000", "45")
        );
        flights.write(
                new Flight("BG-22", "Shiraz", "Tabriz", "1401-12-12",
                        "22:30", "1100000", "12")
        );
        flights.write(
                new Flight("AX-22", "Shiraz", "Tehran", "1401-12-22",
                        "08:00", "500000", "15")
        );
        flights.write(
                new Flight("AX-21", "Shiraz", "Hamedan", "1401-09-22",
                        "08:30", "500000", "55")
        );
        flights.write(
                new Flight("QX-22", "Tehran", "Mashhad", "1401-12-22",
                        "09:00", "600000", "115")
        );
        flights.write(
                new Flight("AS-22", "Tehran", "Esfahan", "1402-02-22",
                        "22:00", "500000", "8")
        );
        flights.write(
                new Flight("AA-22", "Esfahan", "Tehran", "1402-01-02",
                        "13:30", "500000", "10")
        );
        flights.write(
                new Flight("PX-12", "Esfahan", "Shiraz", "1402-10-22",
                        "18:30", "1000000", "100")
        );
    }
}
