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
    public RandomAccessFile open(String fileName) throws FileNotFoundException {
        return this.randomAccessFile =  new RandomAccessFile(fileName, "rw");
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
    public String fixStringToWrite(String str) {
        while (STRING_BUILDER.length() < STRING_SIZE)
            STRING_BUILDER.append(" ");
        str = STRING_BUILDER.toString();
        return str.substring(0, STRING_SIZE);
    }
    public void update(int position, String shouldUpdate) throws IOException {
        randomAccessFile.seek(position);
        randomAccessFile.writeChars(fixStringToWrite(shouldUpdate));
    }
    public void remove(int position, int recordSize, String fileName) throws IOException {

        File tempFile = new File("temp.dat");
        RandomAccessFile temp = new RandomAccessFile(tempFile, "w");
        randomAccessFile.seek(0);
        while (randomAccessFile.getFilePointer() != randomAccessFile.length()) {

            if (randomAccessFile.getFilePointer() != position){
                for (int i = 0; i < recordSize; i = i + STRING_SIZE)
                    temp.writeChars(read());
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
