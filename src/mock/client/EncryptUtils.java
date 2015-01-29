package mock.client;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by archit on 29/1/15.
 */
public class EncryptUtils {
  public static String encryptAES128(String data, String key) {
    byte[] keyRaw = key.getBytes();
    SecretKeySpec skeySpec = new SecretKeySpec(keyRaw, "AES");

    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
      byte[] encrypted = cipher.doFinal(data.getBytes());
      return (new String(Base64.encodeBase64(encrypted)));

    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | InvalidKeyException
        | IllegalBlockSizeException
        | BadPaddingException e) {
    }
    return null;
  }

  public static String decryptAES128(String data, String key) {
    byte[] keyRaw = key.getBytes();
    SecretKeySpec secretKey = new SecretKeySpec(keyRaw, "AES");
    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);

      return new String(cipher.doFinal(Base64.decodeBase64(data)));
    } catch (NoSuchAlgorithmException
        | NoSuchPaddingException
        | InvalidKeyException
        | IllegalBlockSizeException
        | BadPaddingException e) {
    }
    return null;
  }


}
