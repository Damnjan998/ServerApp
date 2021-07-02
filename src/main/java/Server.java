import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    public Server(int port) {
        try(ServerSocket server = new ServerSocket(port)) {

            System.out.println("Client Accepted");

            while (true) {

                Socket socket = server.accept();
                DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream());

                String fileName = in.readUTF();

                if (fileName.equals("EOF")) {
                    break;
                }

                String ext = getExtension(fileName);


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream("C:\\Users\\Ogi\\Desktop\\" + in.readUTF());
                    byte[] bytes = new byte[8192];
                    in.read(bytes);
                    fos.write(bytes);
                    System.out.println(Arrays.toString(bytes));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
            //System.out.println("Closing Connection");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        String extension = "";

        if (index > 0) {
            extension = fileName.substring(index + 1);
        }
        return extension;
    }

    public static void main(String[] args) {
        Server server = new Server(8080);
    }
}
