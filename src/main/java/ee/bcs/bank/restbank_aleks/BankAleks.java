package ee.bcs.bank.restbank_aleks;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BankAleks {
    private List<AccountDtoAleks> accounts = new ArrayList<>();
    private int accountIdCount = 1;

    private List<TransactionDtoAleks> transactions = new ArrayList<>();
    private int  transactionIdCount = 1;

    public void incrementAccountId() {
        accountIdCount++;
    }

    public void addAccountToAccounts(AccountDtoAleks accountDto) {
        accounts.add(accountDto);
    }

    public void addTransactionToTransactions(TransactionDtoAleks transactionDtoAleks) {
        transactions.add(transactionDtoAleks);
    }

    public void incrementTransactionId() {
        transactionIdCount++;
    }
}
