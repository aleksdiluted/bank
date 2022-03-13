package ee.bcs.bank.restbank_aleks;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDtoAleks {
    private int id;
    private int accountId;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Integer amount;
    private Integer balance;
    private LocalDateTime localDateTime;
    private Character transactionType;

}
