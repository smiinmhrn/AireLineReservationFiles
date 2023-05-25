import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
public class FileTemp <E> {
    protected RandomAccessFile randomAccessFile;
    private final int STRING_SIZE = 60;
    private final StringBuilder STRING_BUILDER = new StringBuilder();
    public FileTemp(RandomAccessFile randomAccessFile, String fileName) {
        this.randomAccessFile = randomAccessFile;
    }
    public void open(String fileName) throws FileNotFoundException {
        this.randomAccessFile =  new RandomAccessFile(fileName, "rw");
    }
    public void ifClose(String filename) throws FileNotFoundException {
        try {
            read();
        } catch (IOException e) {
            open(filename);
        }
    }
    public void closeFile() throws IOException {
        randomAccessFile.close();
    }
    public String giveRecord(E object){
        return object.toString();
    }
    public String getSinglePartOfRecord(int position) throws IOException {
        randomAccessFile.seek(position);
        return read();
    }
    public void write(E object) throws IOException {
        randomAccessFile.seek(randomAccessFile.length());
        randomAccessFile.writeChars(giveRecord(object));
    }
    public String read() throws IOException {
        String str = "";
        for (int i = 0; i < 30; i++) {
            str += randomAccessFile.readChar();
        }
//        System.out.println(str.trim());
        return str.trim();
    }
    public int search(int position, String shouldSearch, int recordSize) throws IOException {
        randomAccessFile.seek(position);
        while (randomAccessFile.getFilePointer() != randomAccessFile.length()){
            if (shouldSearch.equals(read())) {
                return (int) randomAccessFile.getFilePointer();
            }
            else{
                if ((randomAccessFile.getFilePointer() + (recordSize - (STRING_SIZE)) > randomAccessFile.length()))
                    break;
                else
                    randomAccessFile.seek(randomAccessFile.getFilePointer() + (recordSize - (STRING_SIZE)));
            }
        } return -1;
    }
    public String fixStringToWrite(String str){
        while (str.length() < 30)
            str += " ";
        return str.substring(0,30);
    }
    public void update(int position, String shouldUpdate) throws IOException {
        randomAccessFile.seek(position);
        randomAccessFile.writeChars(fixStringToWrite(shouldUpdate));
    }
    public void remove(int position, int recordSize, String fileName) throws IOException {
        System.out.println(position);
        File tempFile = new File("temp.dat");
        RandomAccessFile temp = new RandomAccessFile(tempFile, "rw");
        randomAccessFile.seek(0);
        while (randomAccessFile.getFilePointer() != randomAccessFile.length()) {

            if (randomAccessFile.getFilePointer() != (position - 60)){
                for (int i = 0; i < recordSize; i = i + STRING_SIZE)
                    temp.writeChars(fixStringToWrite(read()));
            }else {
                long seek = randomAccessFile.getFilePointer() + recordSize;
                if (seek < randomAccessFile.length())
                    randomAccessFile.seek(seek);
            }
        }
        randomAccessFile.close();
        new File(fileName).delete();
        tempFile.renameTo(new File(fileName));
    }
}
