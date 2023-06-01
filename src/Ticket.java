public class Ticket implements CanCustomize{
    private final String USERNAME;
    private final String FLIGHT_ID;
    private final String TICKET_ID;
    public Ticket(String username, String flightId, String ticketId) {
        this.USERNAME = username;
        this.FLIGHT_ID = flightId;
        this.TICKET_ID = ticketId;
    }
    // for customizing string for writing in file
    @Override
    public String customize(String str){
        while (str.length() < 30)
            str += " ";
        return str.substring(0,30);
    }
    @Override
    public String toString() {
        return customize(USERNAME) + customize(FLIGHT_ID) + customize(TICKET_ID);
    }
}
