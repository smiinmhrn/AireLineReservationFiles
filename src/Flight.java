// this class is for get filed of class and set them
public class Flight implements CanCustomize{
    private String flightId;
    private String origin;
    private String destination;
    private String date;
    private String time;
    private String price;
    private String seats;
    private final StringBuilder STRING_BUILDER = new StringBuilder();
    public Flight(String flightId, String origin, String destination,
                   String date, String time, String price, String seats) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.price = price;
        this.seats = seats;
    }
    @Override
    public String customize(String str) {
        while (str.length() < 30)
            str += " ";
        return str.substring(0,30);
    }
    @Override
    public String toString() {
        return customize(flightId) + customize(origin) + customize(destination) + customize(date)
                + customize(time) + customize(price) + customize(seats);
    }
}
