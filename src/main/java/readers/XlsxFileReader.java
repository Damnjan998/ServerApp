package readers;

import com.netconomy.encryption.suite.Encryptor;
import database.DatabaseConnector;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class XlsxFileReader implements FileReader {

    DatabaseConnector db = new DatabaseConnector();

    @Override
    public void read(String dbPath, ZipOutputStream zos, String fileName, DataInputStream dataInputStream)
            throws IOException {

        int lastIndex = dataInputStream.readInt();

        String key = db.findKey(fileName, dbPath);

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transaction");

        for (int i = 1; i < lastIndex; i++) {

            Row row = sheet.createRow(i);

            Cell cell1 = row.createCell(0);
            cell1.setCellValue(Encryptor.decrypt(key, dataInputStream.readUTF()));

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(Encryptor.decrypt(key, dataInputStream.readUTF()));

            Cell cell3 = row.createCell(2);
            cell3.setCellValue(Encryptor.decrypt(key, dataInputStream.readUTF()));
        }

        addFileToZip(fileName, workbook, zos);
    }

    private void addFileToZip(String fileName, Workbook workbook, ZipOutputStream zos) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        bos.writeTo(zos);

        zos.closeEntry();
        workbook.close();
    }
}
