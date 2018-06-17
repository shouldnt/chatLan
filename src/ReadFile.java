import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//This class is used to Read the SERVER'ip address and its port
public class ReadFile {
    String path;
    FileReader fileRead;
    BufferedReader buff;

    int serverPort;
    String serverName;
    public ReadFile()
    {
        path="";
        fileRead=null;
        buff=null;
        serverName="";
        serverPort=0;
    }
    public ReadFile(String path)
    {
        this.path=path;
        fileRead=null;
        buff=null;
        serverName="";
        serverPort=0;
    }
    public void read()
    {
        try {
            try {
                fileRead = new FileReader(path);
                buff = new BufferedReader(fileRead);

                serverName = buff.readLine();
                serverPort = Integer.parseInt(buff.readLine());
            }
            catch (FileNotFoundException f)
            {
                System.out.print("Cannot find the info !");
            }
        }
        catch (IOException e)
        {
            System.out.print("Error occured !");
        }
        finally {
            try {
                if(fileRead!=null)
                    fileRead.close();
                if(buff!=null)
                    buff.close();
            }
            catch (IOException e)
            {

            }
        }
    }
    public int getPort()
    {
        return this.serverPort;
    }
    public String getName()
    {
        return this.serverName;
    }
}
