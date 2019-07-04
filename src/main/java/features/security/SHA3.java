package core.java9.features.security;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA3 {

  public static void main(final String[] args) throws NoSuchAlgorithmException {
    final MessageDigest instance = MessageDigest.getInstance("SHA3-224");
    final byte[] digest = instance.digest("".getBytes());
    System.out.println(Hex.encodeHexString(digest));
  }
}
