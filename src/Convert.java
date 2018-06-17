import java.math.BigInteger;

public class Convert {
    public static BigInteger StringtoBigInt(String s)
    {
        byte[] bytes = s.getBytes();
        BigInteger message = new BigInteger(bytes);
        return message;
    }
    public static String BigInttoString(BigInteger i)
    {
        String temp = new String(i.toByteArray());
        return temp;
    }
}
