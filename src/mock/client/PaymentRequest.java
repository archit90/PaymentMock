package mock.client;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by archit on 29/1/15.
 */
public class PaymentRequest {
  private HashMap<String, Object> paramMap = new HashMap<>();

  public static class Constants {
    public static final String BANK_IFSC_CODE = "bank_ifsc_code";
    public static final String BANK_ACCOUNT_NUMBER = "bank_account_number";
    public static final String AMOUNT = "amount";
    public static final String MERCHANT_TRX_REF = "merchant_transaction_ref";
    public static final String TRANSACTION_DATE = "transaction_date";
    public static final String PAYMENT_GATEWAY_MERCHANT_REF = "payment_gateway_merchant_reference";
    public static final SimpleDateFormat DATE_YMD = new SimpleDateFormat("yyyy-MM-dd");
    public static final ArrayList<String> paramList = new ArrayList<>();

    static {
      paramList.add(BANK_IFSC_CODE);
      paramList.add(BANK_ACCOUNT_NUMBER);
      paramList.add(AMOUNT);
      paramList.add(MERCHANT_TRX_REF);
      paramList.add(TRANSACTION_DATE);
      paramList.add(PAYMENT_GATEWAY_MERCHANT_REF);
    }
  }

  public void addBankIfscCode(String bankIfscCode) {
    if (StringUtils.isNotEmpty(bankIfscCode)) {
      paramMap.put(Constants.BANK_IFSC_CODE, bankIfscCode);
    }
  }


  public void addBankAccountNumber(long bankAccountNumber) {
    if (bankAccountNumber > 0) {  // and possibly 10? digits
      paramMap.put(Constants.BANK_ACCOUNT_NUMBER, bankAccountNumber);
    }
  }


  public void addAmount(double amount) {
    paramMap.put(Constants.AMOUNT, amount);
  }


  public void addMerchantTrxRef(String merchantTrxRef) {
    if (StringUtils.isNotEmpty(merchantTrxRef)) {
      paramMap.put(Constants.MERCHANT_TRX_REF, merchantTrxRef);
    }
  }


  public void addTransactionDate(Date transactionDate) {
    paramMap.put(Constants.TRANSACTION_DATE, Constants.DATE_YMD.format(transactionDate));
  }


  public void addPaymentGatewayRefNumber(String paymentGatewayRefNumber) {
    if (StringUtils.isNotEmpty(paymentGatewayRefNumber)) {
      paramMap.put(Constants.PAYMENT_GATEWAY_MERCHANT_REF, paymentGatewayRefNumber);
    }
  }

  public String generateRequest() {
    String paramString = genParamsString();
    byte[] hash = SHAUtils.getSHAdigest(paramString);
    paramString += "|hash=" + StringUtils.join(hash);
    String crypt = EncryptUtils.encryptAES128(paramString, KeyStore.KEY);
    return crypt;

  }

  private String genParamsString() {
    StringBuffer sb = new StringBuffer();
    String sep = "";
    for (String param : Constants.paramList) {
      sb.append(sep + param + "=" + paramMap.get(param));
      sep = "|";
    }
    return sb.toString();
  }
}
