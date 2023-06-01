import java.io.IOException;
import java.io.RandomAccessFile;
public class Users extends FileTemp{

    public Users(RandomAccessFile randomAccessFile) {
        super(randomAccessFile);
    }
    // this function is use for changing user password
    public void changePass(String username, String newPass) throws IOException {
        update((search(0, username,180)), newPass);
    }
    // this function is use for if user charge is enough for buying or not
    public boolean isEnough(String shouldPay, String username) throws IOException {
        return Long.parseLong(getSinglePartOfRecord(search(0,username,180) + 60)) >=
                Long.parseLong(shouldPay);
    }
    // this function is use for increasing charge
    public void increaseCharge(String username, String chargeAmount) throws IOException {
        int position = search(0, username,180);
        String oldCharge = getSinglePartOfRecord((position + 60));
        long newCharge = Long.parseLong(oldCharge) + Long.parseLong(chargeAmount);
        update((position + 60), String.valueOf(newCharge));
    }
    // this function is use for decreasing charge
    public void decreaseCharge(String username, String chargeAmount) throws IOException {
        int position = search(0, username, 180);
        String oldCharge = getSinglePartOfRecord((position + 60));
        long newCharge = Long.parseLong(oldCharge) - Long.parseLong(chargeAmount);
        update((position + 60), String.valueOf(newCharge));
    }
}
