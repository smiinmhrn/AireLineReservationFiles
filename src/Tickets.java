import java.io.IOException;
import java.io.RandomAccessFile;
public class Tickets extends FileTemp{
    public Tickets(RandomAccessFile randomAccessFile) {
        super(randomAccessFile);
    }
    // this function is for creating ticket id
    public String creatTicketId(String username, String flightId) throws IOException {

        String ticketId = username + flightId;
        if (randomAccessFile.length() == 0 ) return ticketId;

        while (true){
            if (search(120, ticketId, 180) != -1) ticketId = ticketId + "#";
            else break;
        } return ticketId;
    }
    // this function is use for print all user ticket id
    public void printAllUserTickets(String username) throws IOException {
        randomAccessFile.seek(0);
        while (randomAccessFile.getFilePointer() != randomAccessFile.length()){
            String ticket = read();
            if (ticket.startsWith(username) && (!ticket.equals(username))) System.out.println(ticket);
        }
    }
    // this function is showing that a user have ever bought a ticket or not
    public boolean haveEverBooked(String username) throws IOException {
        return search(0, username, 180) != -1;
    }

}
