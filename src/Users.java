import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
public class Users extends FileTemp{

    public Users(RandomAccessFile randomAccessFile, String fileName) {
        super(randomAccessFile, fileName);
    }
    public void changePass(String username, String newPass) throws IOException {
        update((search(0, username,180)), newPass);
    }
    public boolean isEnough(String shouldPay, String username) throws IOException {
        return Long.parseLong(getSinglePartOfRecord(search(0,username,180) + 60)) >=
                Long.parseLong(shouldPay);
    }
    public void increaseCharge(String username, String chargeAmount) throws IOException {
        int position = search(0, username,180);
        String oldCharge = getSinglePartOfRecord((position + 60));
        long newCharge = Long.parseLong(oldCharge) + Long.parseLong(chargeAmount);
        update((position + 60), String.valueOf(newCharge));
    }
    public void decreaseCharge(String username, String chargeAmount) throws IOException {
        int position = search(0, username, 180);
        String oldCharge = getSinglePartOfRecord((position + 60));
        long newCharge = Long.parseLong(oldCharge) - Long.parseLong(chargeAmount);
        update((position + 60), String.valueOf(newCharge));
    }
}
