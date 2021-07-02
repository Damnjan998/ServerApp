package readers;

public class FileExtensionReader {

    public static FileReader getFileReader(String extension) {

        switch (extension) {
            case "txt":
                return new TxtFileReader();
            case "xlsx":
                return new XlsxFileReader();
            default:
                return null;
        }
    }
}
