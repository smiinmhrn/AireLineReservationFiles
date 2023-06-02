import java.io.IOException;
import java.io.RandomAccessFile;
public class Flights extends FileTemp{
    public Flights(RandomAccessFile randomAccessFile) {
        super(randomAccessFile);
    }
    // this function is for printing single airline in console
    public void printSingleAirline() throws IOException {
        System.out.printf("| %-12s | %-12s | %-12s | %-12s | %-12s | %-12s | %-12s ",
                read(), read(), read(),
                read(), read(), read(), read());
        System.out.print("\n");
    }
    // this function is for printing all airlines in console
    public void printAllAirline() throws IOException {
        randomAccessFile.seek(0);
        long length = randomAccessFile.length();
        while (length != 0){
            printSingleAirline();
            length = length - 420;
        }
    }
    // this function is for showing if there is any seats available in airline for buying
    public boolean availableSeat(int position) throws IOException {
        return getSinglePartOfRecord(position).equals("0");
    }
    // this function is for increasing seats when someone cancelling an airline
    public void increaseSeats(int position) throws IOException {
        randomAccessFile.seek(position);
        int seat = Integer.parseInt(read()) + 1;
        update(position, String.valueOf(seat));
    }
    // this function is for decreasing seats when someone buying an airline
    public void decreaseSeatsSeats(int position) throws IOException {
        randomAccessFile.seek(position);
        int seat = Integer.parseInt(read()) - 1;
        update(position, String.valueOf(seat));
    }
}
