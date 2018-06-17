import java.math.BigInteger;
import java.security.SecureRandom;

//This class is the implementation of RSA Algorithm
public class AlgorithmRSA {
    //d: private key, n,e: public key
    private BigInteger n, d, e;

    public BigInteger getN()
    {
        return n;
    }
    public BigInteger getE()
    {
        return e;
    }
    public AlgorithmRSA() {

    }
    public AlgorithmRSA(int N) {
        KeyRSA(N);
    }
    public AlgorithmRSA(int N,BigInteger n, BigInteger e) {
        KeyRSA(N);
        this.n=n;
        this.e=e;
    }

    public void KeyRSA(int bits){
        SecureRandom r = new SecureRandom();//create BigInteger r random
        BigInteger p = new BigInteger(bits , 100, r);
        BigInteger q = new BigInteger(bits , 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        boolean found = false;
        do {
            e = new BigInteger(bits , 50, r);
            if (m.gcd(e).equals(BigInteger.ONE) && e.compareTo(m) < 0) {
                found = true;
            }
        } while (!found);
        d = e.modInverse(m);

    }

    // Encrypt the given plaintext message.Use public key decrypt

    public synchronized String encrypt(String message, BigInteger n, BigInteger e) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }


    //Encrypt the given plaintext message.Use public key decrypt

    public synchronized BigInteger encrypt(BigInteger message,BigInteger n, BigInteger e) {
        return message.modPow(e, n);
    }


    // Decrypt the given ciphertext message.Use private key decrypt

    public synchronized String decrypt(String message) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }


    // Decrypt the given ciphertext message.Use private key decrypt

    public synchronized BigInteger decrypt(BigInteger message) {

        return message.modPow(d, n);
    }


}
