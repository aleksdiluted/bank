package ee.bcs.bank.restbank_aleks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResultAleks {
    private int accountId;
    private int transactionId;
    private String message;
    private String error;
}
