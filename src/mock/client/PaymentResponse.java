package mock.client;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by archit on 29/1/15.
 */
public class PaymentResponse {
  private static final HashMap<String, Object> paramMap = new HashMap<>();

  public static class Constants {
    public static final String TRX_STATUS = "trx_status";
    public static final String AMOUNT = "amount";
    public static final String MERCHANT_TRX_REF = "merchant_transaction_ref";
    public static final String TRANSACTION_DATE = "transaction_date";
    public static final String PAYMENT_GATEWAY_MERCHANT_REF = "payment_gateway_merchant_ref";
    public static final String PAYMENT_GATEWAY_TRX_REF = "payment_gateway_transaction_ref";

    public static final ArrayList<String> paramList = new ArrayList<>();

    static {
      paramList.add(TRX_STATUS);
      paramList.add(AMOUNT);
      paramList.add(MERCHANT_TRX_REF);
      paramList.add(TRANSACTION_DATE);
      paramList.add(PAYMENT_GATEWAY_MERCHANT_REF);
      paramList.add(PAYMENT_GATEWAY_TRX_REF);
    }
  }

  public boolean parseResponse(String data){
    String decrypt = EncryptUtils.decryptAES128(data, KeyStore.KEY);
    int endIndex = decrypt.lastIndexOf("|");
    String paramString = decrypt.substring(0, endIndex);
    String[] split = decrypt.substring(endIndex + 1).split("=");
    if(split.length<2)
      return false;
    String hashOrig = split[1];
      byte[] hash = SHAUtils.getSHAdigest(paramString);
      if(hash.equals(hashOrig)){
        setParams(data); // this function sets  paramMap
        return true;
      }
      else {
        return false;
      }
  }
  private void setParams(String data){

  }
}
