package mock.client;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * Created by archit on 29/1/15.
 */
public class SHAUtils {
  public static byte[] getSHAdigest(String data) {
    MessageDigest digest = DigestUtils.getSha1Digest();
    return digest.digest(data.getBytes());
  }

}
