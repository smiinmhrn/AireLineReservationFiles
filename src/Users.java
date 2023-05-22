import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
public class Users extends FileTemp{
    public Users(RandomAccessFile randomAccessFile, String fileName, File file, int RECORD_SIZE, int STRING_SIZE) {
        super(randomAccessFile, fileName, file, RECORD_SIZE, STRING_SIZE);
    }
    public void changePass(String username, String newPass) throws IOException {
        update(search(0, username), newPass);
    }
    public boolean isEnough(String shouldPay, String username) throws IOException {
        return Long.parseLong(getSinglePartOfRecord(search(60,username))) >= Long.parseLong(shouldPay);
    }
    public void increaseCharge(String username, String chargeAmount) throws IOException {
        int position = search(60, username);
        String oldCharge = getSinglePartOfRecord(position);
        long newCharge = Long.parseLong(oldCharge) + Long.parseLong(chargeAmount);
        update(position, String.valueOf(newCharge));
    }
    public void decreaseCharge(String username, String chargeAmount) throws IOException {
        int position = search(60, username);
        String oldCharge = getSinglePartOfRecord(position);
        long newCharge = Long.parseLong(oldCharge) - Long.parseLong(chargeAmount);
        update(position, String.valueOf(newCharge));
    }
}
