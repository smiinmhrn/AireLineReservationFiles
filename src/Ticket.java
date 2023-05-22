public class Ticket implements CanCustomize{
    private final String USERNAME;
    private final String FLIGHT_ID;
    private final String TICKET_ID;
    private final StringBuilder STRING_BUILDER = new StringBuilder();
    public Ticket(String username, String flightId, String ticketId) {
        this.USERNAME = username; //0-29
        this.FLIGHT_ID = flightId; // 30-60
        this.TICKET_ID = ticketId; // 60-90
    }
    @Override
    public String customize(String str) {
        while (STRING_BUILDER.length() < 30)
            STRING_BUILDER.append(" ");
        str = STRING_BUILDER.toString();
        return str.substring(0, 30);
    }
    @Override
    public String toString() {
        return customize(USERNAME) + customize(FLIGHT_ID) + customize(TICKET_ID);
    }
}
