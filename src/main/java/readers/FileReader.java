package readers;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public interface FileReader {

    void read(String dbPath, ZipOutputStream zos, String fileName, DataInputStream dataInputStream) throws IOException;
}
