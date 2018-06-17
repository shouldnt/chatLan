import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
//This class is used to Write the Public Key to a File text
public class CreateKey {
    BigInteger n=null, e=null;
    String fileName="";
    PrintWriter fileWrite;

    public CreateKey(BigInteger e, BigInteger n,String name)
    {
        this.fileName=name;
        this.e=e;
        this.n=n;
    }
    public void write()
    {
        try{
            fileWrite = new PrintWriter(fileName, "UTF-8");
            fileWrite.println(e);
            fileWrite.println(n);
            fileWrite.close();
        } catch (IOException e) {
        }
    }
}
