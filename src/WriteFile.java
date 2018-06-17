import java.io.IOException;
import java.io.PrintWriter;

//This class is used to Write the SERVER'ip address and SERVER's port to the File Text
public class WriteFile {
    String serverName;
    int port;
    PrintWriter fileWrite;

    public WriteFile()
    {
        serverName="";
        port=0;
        fileWrite=null;
    }
    public WriteFile(String name, int port)
    {
        this.serverName=name;
        this.port=port;
        fileWrite=null;
    }
    public void write()
    {
        try{
            fileWrite = new PrintWriter("SERVER.txt", "UTF-8");
            fileWrite.println(serverName);
            fileWrite.println(port);
            fileWrite.close();
        } catch (IOException e) {
        }
    }
}
