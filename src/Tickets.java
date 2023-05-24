import java.io.IOException;
import java.io.RandomAccessFile;
public class Tickets extends FileTemp{
    public Tickets(RandomAccessFile randomAccessFile, String fileName) {
        super(randomAccessFile, fileName);
    }
    public String creatTicketId(String username, String flightId) throws IOException {
        String ticketId = username + flightId;

        while (true){
            if (search(60, ticketId, 90) != -1) ticketId = ticketId + "#";
            else break;
        } return ticketId;
    }
    public void printAllUserTickets(String username) throws IOException {
        randomAccessFile.seek(0);
        while (randomAccessFile.getFilePointer() != randomAccessFile.length()){
            String ticket = read();
            if (ticket.startsWith(username) && (!ticket.equals(username))) System.out.println(ticket);
        }
    }
    public boolean haveEverBooked(String username) throws IOException {
        return search(0, username, 90) != -1;
    }
}
