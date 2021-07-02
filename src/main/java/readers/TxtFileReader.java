package readers;

import com.netconomy.encryption.suite.Encryptor;
import database.DatabaseConnector;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TxtFileReader implements FileReader {

    DatabaseConnector db = new DatabaseConnector();

    @Override
    public void read(String dbPath, ZipOutputStream zos, String fileName, DataInputStream dataInputStream) throws IOException {

        String key = db.findKey(fileName, dbPath);
        String fileBody = Encryptor.decrypt(key, dataInputStream.readUTF());

        addFileToZip(fileName, fileBody, zos);
    }

    private void addFileToZip(String fileName, String fileBody, ZipOutputStream zos) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);

        zos.putNextEntry(zipEntry);
        zos.write(fileBody.getBytes(), 0, fileBody.getBytes().length);
        zos.closeEntry();
    }
}
