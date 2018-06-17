import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

//This Class is used to Read Public Key from the File Text
public class GetKey {
    BigInteger n=null, e = null;
    String filePath="";

    FileReader fileRead;
    BufferedReader buff;
    public GetKey(String fileName)
    {
        this.filePath=fileName;
    }
    public void read()
    {
        try {
            try {
                fileRead = new FileReader(filePath);
                buff = new BufferedReader(fileRead);

                String t =buff.readLine();
                this.e=new BigInteger(t);

                t=buff.readLine();
                this.n=new BigInteger(t);
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
    public BigInteger getE()
    {
        return this.e;
    }
    public BigInteger getN()
    {
        return this.n;
    }
}
