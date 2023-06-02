import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
public class FileTemp <E> {
    protected RandomAccessFile randomAccessFile;
    private final int STRING_SIZE = 60;
    public FileTemp(RandomAccessFile randomAccessFile) {
        this.randomAccessFile = randomAccessFile;
    }
    // use for open files
    public void open(String fileName) throws FileNotFoundException {
        this.randomAccessFile =  new RandomAccessFile(fileName, "rw");
    }
    // use for check if file close and open it then
    public void ifClose(String filename) throws FileNotFoundException {
        if (this.randomAccessFile == null) open(filename);
    }
    //use for closing files
    public void closeFile() throws IOException {
        randomAccessFile.close();
    }
    //use for creating a record of an object
    private String giveRecord(E object){
        return object.toString();
    }
    //use for when we want a special par of a record
    public String getSinglePartOfRecord(int position) throws IOException {
        randomAccessFile.seek(position);
        return read();
    }
    //use for writing in file
    public void write(E object) throws IOException {
        randomAccessFile.seek(randomAccessFile.length());
        randomAccessFile.writeChars(giveRecord(object));
    }
    //use for reading from file
    public String read() throws IOException {
        String str = "";
        for (int i = 0; i < 30; i++)
            str += randomAccessFile.readChar();
        return str.trim();
    }
    //use for searching in file
    public int search(int position, String shouldSearch, int recordSize) throws IOException {
        if (randomAccessFile.length() == 0) return -1;

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
    //use for customize strings for writing
    public String fixStringToWrite(String str){
        while (str.length() < 30)
            str += " ";
        return str.substring(0,30);
    }
    //use for update a part of file
    public void update(int position, String shouldUpdate) throws IOException {
        randomAccessFile.seek(position);
        randomAccessFile.writeChars(fixStringToWrite(shouldUpdate));
    }
    //use for removing part of file
    public void remove(int position, int recordSize, int timeToRead) throws IOException {

        position = position - 60;
        long len = randomAccessFile.length();
        len = len - recordSize;
        long setLen = len;

        if (recordSize == randomAccessFile.length()) randomAccessFile.setLength(0);
        else {
            if (position + 420 == randomAccessFile.length()) randomAccessFile.setLength(setLen);
            else {
                randomAccessFile.seek(len);
                for (int i = 0; i < timeToRead ; i++) {
                    update(position, read());
                    position = position + STRING_SIZE;
                    len = len + STRING_SIZE;
                    randomAccessFile.seek(len);
                }
                randomAccessFile.setLength(setLen);
            }
        }
    }

    // this method in also using for removing file but with creating temp file and delete an old one and rename new one
//    public void remove(int position, int recordSize, String fileName) throws IOException {
//
//        RandomAccessFile temp = new RandomAccessFile("temp.dat", "rw");
//
//        randomAccessFile.seek(0);
//        while (randomAccessFile.getFilePointer() != randomAccessFile.length()) {
//
//            if (randomAccessFile.getFilePointer() != (position - 60)){
//                for (int i = 0; i < recordSize; i = i + STRING_SIZE)
//                    temp.writeChars(fixStringToWrite(read()));
//            }else {
//                long seek = randomAccessFile.getFilePointer() + recordSize;
//                if (seek < randomAccessFile.length())
//                    randomAccessFile.seek(seek);
//            }
//        }
//        randomAccessFile.close();
//
//        File file = new File(fileName);
//        File tempFile = new File("temp.dat");
//
//        new File(fileName).delete();
//        tempFile.renameTo(new File(fileName));
//    }
}
