import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
public class FileTemp <E> {
    protected RandomAccessFile randomAccessFile;
    private String fileName;
    private File file;
    private final int RECORD_SIZE;
    private final int STRING_SIZE;
    private final StringBuilder STRING_BUILDER = new StringBuilder();
    public FileTemp(RandomAccessFile randomAccessFile, String fileName, File file, int RECORD_SIZE, int STRING_SIZE) {
        this.randomAccessFile = randomAccessFile;
        this.fileName = fileName;
        this.file = file;
        this.RECORD_SIZE = RECORD_SIZE;
        this.STRING_SIZE = STRING_SIZE;
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
        for (int i = 0; i < STRING_SIZE; i++)
            STRING_BUILDER.append(randomAccessFile.readChar());
        return STRING_BUILDER.toString().trim();
    }
    public int search(int position, String shouldSearch) throws IOException {
        randomAccessFile.seek(position);
        while (randomAccessFile.getFilePointer() != randomAccessFile.length()){
            if (shouldSearch.equals(read())) return position;
            else
                randomAccessFile.seek(randomAccessFile.getFilePointer() + (RECORD_SIZE - STRING_SIZE));
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
    public void remove(int position) throws IOException {

        File tempFile = new File("temp.dat");
        RandomAccessFile temp = new RandomAccessFile(tempFile, "w");
        randomAccessFile.seek(0);
        while (randomAccessFile.getFilePointer() != randomAccessFile.length()) {

            if (randomAccessFile.getFilePointer() != position){
                for (int i = 0; i < RECORD_SIZE; i = i + STRING_SIZE) {
                    temp.writeChars(read());
                    temp.writeBytes("\r\n");
                }
            }else {
                long seek = randomAccessFile.getFilePointer() + RECORD_SIZE;
                if (seek < randomAccessFile.length())
                    randomAccessFile.seek(seek);
            }
        }
//        this.randomAccessFile = temp;
//        file.delete();
//        tempFile.
    }
}
