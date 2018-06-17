import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
//This Class is used to Send File and Receive File throught Socket
public class FileTransfer implements Runnable {
    public final static int FILE_SIZE = 6022386;

    String ip = "";
    int port = 1102;
    String fileName = "";

    Socket socket = null;
    ServerSocket serverSocket = null;
    String message = "";

    public FileTransfer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    public void setFilePath(String path)
    {
        this.fileName=path;
    }
    public void sendFile(String fileName) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;

        try {
            serverSocket = new ServerSocket(port);
            try {
                socket = serverSocket.accept();

                File myFile = new File(fileName);
                byte[] mybytearray = new byte[(int) myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray, 0, mybytearray.length);
                os = socket.getOutputStream();
                os.write(mybytearray, 0, mybytearray.length);
                os.flush();
                System.out.println("Done.");
                message = "1 file send with " + mybytearray.length + "bytes";
            } catch (IOException e) {
                message = "File send failed!";
            } finally {
                bis.close();
                os.close();
                socket.close();
            }
            //}
        } finally {
            if (serverSocket != null)
                serverSocket.close();
        }
    }

    public void receiveFile(String fileName) throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            socket = new Socket(ip, port);
            // receive file
            byte[] mybytearray = new byte[6022386];
            InputStream is = socket.getInputStream();
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
            message = "File downloaded ! " + current + "bytes";
        } catch (IOException e) {
            message = "File download failed";
        } finally {
            fos.close();
            bos.close();
            socket.close();
        }
    }
    public void receiveFile() throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            socket = new Socket(ip, port);
            // receive file
            byte[] mybytearray = new byte[6022386];
            InputStream is = socket.getInputStream();
            fos = new FileOutputStream(this.fileName);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1);

            bos.write(mybytearray, 0, current);
            bos.flush();
            message = "File downloaded ! " + current + "bytes";
        } catch (IOException e) {
            message = "File download failed";
        } finally {
            fos.close();
            bos.close();
            socket.close();
        }
    }

    public String getMessage() {
        return message;
    }

    public void run()
    {
        try {
            receiveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
