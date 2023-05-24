import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
public class Flights extends FileTemp{
    private final int RECORD_SIZE = 210;
    public Flights(RandomAccessFile randomAccessFile, String fileName) {
        super(randomAccessFile, fileName);
    }
    public void printSingleAirline() throws IOException {
        System.out.printf("| %-12s | %-12s | %-12s | %-12s | %-12s | %-12s | %-12s ",
                read(), read(), read(), read(), read(), read(), read());
        System.out.print("\n");
    }
    public void printAllAirline() throws IOException {
        for (int i = 0; i < randomAccessFile.length(); i++)
            printSingleAirline();
    }
    public boolean availableSeat(int position) throws IOException {
        return getSinglePartOfRecord(position).equals("0");
    }
    public void increaseSeats(int position) throws IOException {
        randomAccessFile.seek(position);
        int seat = Integer.parseInt(read()) + 1;
        update(position, String.valueOf(seat));
    }
    public void decreaseSeatsSeats(int position) throws IOException {
        randomAccessFile.seek(position);
        int seat = Integer.parseInt(read()) - 1;
        update(position, String.valueOf(seat));
    }
}
