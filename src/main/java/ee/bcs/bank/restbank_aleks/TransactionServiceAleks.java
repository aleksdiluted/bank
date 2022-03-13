package ee.bcs.bank.restbank_aleks;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceAleks {

    public static final String ATM = "ATM";
    public static final char SEND_MONEY = 's';
    public static final char NEW_ACCOUNT = 'n';
    public static final char DEPOSIT = 'd';
    public static final char WITHDRAWAL = 'w';
    public static final char RECEIVE_MONEY = 'r';

    @Resource
    private AccountServiceAleks accountServiceAleks;

    @Resource
    private BalanceServiceAleks balanceServiceAleks;

//    transactionType
//    n - new account
//    d - deposit
//    w - withdrawal
//    s - send money
//    r - receive money


    // TODO:    createExampleTransaction()
    //  account id 123
    //  balance 1000
    //  amount 100
    //  transactionType 's'
    //  receiver EE123
    //  sender EE456

    public TransactionDtoAleks createExampleTransaction() {
        TransactionDtoAleks transactionDtoAleks = new TransactionDtoAleks();
        transactionDtoAleks.setAccountId(123);
        transactionDtoAleks.setBalance(1000);
        transactionDtoAleks.setAmount(100);
        transactionDtoAleks.setTransactionType(SEND_MONEY);
        transactionDtoAleks.setReceiverAccountNumber("EE123");
        transactionDtoAleks.setSenderAccountNumber("EE456");
        transactionDtoAleks.setLocalDateTime(LocalDateTime.now());
        return transactionDtoAleks;
    }

    public RequestResultAleks addNewTransaction(BankAleks bankAleks, TransactionDtoAleks transactionDtoAleks) {
        // loon vajalikud (tühjad) objektid
        RequestResultAleks requestResultAleks = new RequestResultAleks();

        // vajalike andmete lisamine muutujatesse
        List<AccountDtoAleks> accounts = bankAleks.getAccounts();
        int accountId = transactionDtoAleks.getAccountId();

        // kontrollime, kas konto eksisteerib
        if (!accountServiceAleks.accountIdExists(accounts, accountId)) {
            requestResultAleks.setAccountId(accountId);
            requestResultAleks.setError("Account ID " + accountId + " does not exist!");
            return requestResultAleks;
        }

        // veel vajalike andmete lisamine muutujatesse
        Character transactionType = transactionDtoAleks.getTransactionType();
        Integer amount = transactionDtoAleks.getAmount();
        bankAleks.getTransactionIdCount();
        int transactionId = bankAleks.getTransactionIdCount();


        // pärime välja accountID abiga õige konto ja balance-i
        AccountDtoAleks account = accountServiceAleks.getAccountById(accounts, accountId);
        Integer balance = account.getBalance();

        // töötleme läbi erinevad olukorrad
        int newBalance;
        String receiverAccountNumber;


        // String receiverAccountNumber = transactionDto.getReceiverAccountNumber();
        switch (transactionType) {
            case NEW_ACCOUNT:

                // täidame ära transactionDto
                transactionDtoAleks.setSenderAccountNumber(null);
                transactionDtoAleks.setReceiverAccountNumber(null);
                transactionDtoAleks.setBalance(0);
                transactionDtoAleks.setAmount(0);
                transactionDtoAleks.setLocalDateTime(LocalDateTime.now());
                transactionDtoAleks.setId(transactionId);

                //lisame tehingu transactionite alla (pluss increment)
                bankAleks.addTransactionToTransactions(transactionDtoAleks);
                bankAleks.incrementTransactionId();

                // meisterdame valmis result objekti
                requestResultAleks.setTransactionId(transactionId);
                requestResultAleks.setAccountId(accountId);
                requestResultAleks.setMessage("Successfully added 'new account' transaction");
                return requestResultAleks;


            case DEPOSIT:
                // arvutame välja uue balance
                newBalance = balance + amount;

                // täidame ära transactionDto
                transactionDtoAleks.setSenderAccountNumber(ATM);
                transactionDtoAleks.setReceiverAccountNumber(account.getAccountNumber());
                transactionDtoAleks.setBalance(newBalance);
                transactionDtoAleks.setLocalDateTime(LocalDateTime.now());
                transactionDtoAleks.setId(transactionId);

                //lisame tehingu transactionite alla (pluss inkrementeerime)
                bankAleks.addTransactionToTransactions(transactionDtoAleks);
                bankAleks.incrementTransactionId();

                // uuendame konto balance'it
                account.setBalance(newBalance);

                // meisterdame valmis result objekti
                requestResultAleks.setTransactionId(transactionId);
                requestResultAleks.setAccountId(accountId);
                requestResultAleks.setMessage("Successfully made deposit transaction");
                return requestResultAleks;

            case WITHDRAWAL:
                // arvutame välja uue balance
                if (!balanceServiceAleks.enoughMoneyOnAccount(balance, amount)) {
                    requestResultAleks.setAccountId(accountId);
                    requestResultAleks.setError("Insufficient funds " + amount);
                    return requestResultAleks;
                }

                // arvutame välja uue balance'i
                newBalance = balance - amount;

                // täidame ära transactionDto
                transactionDtoAleks.setSenderAccountNumber(account.getAccountNumber());
                transactionDtoAleks.setReceiverAccountNumber(ATM);
                transactionDtoAleks.setBalance(newBalance);
                transactionDtoAleks.setLocalDateTime(LocalDateTime.now());
                transactionDtoAleks.setId(transactionId);

                //lisame tehingu transactionite alla (pluss inkrementeerime)
                bankAleks.addTransactionToTransactions(transactionDtoAleks);
                bankAleks.incrementTransactionId();

                // uuendame konto balance'it
                account.setBalance(newBalance);

                // meisterdame valmis result objekti
                requestResultAleks.setTransactionId(transactionId);
                requestResultAleks.setAccountId(accountId);
                requestResultAleks.setMessage("Successfully made withdrawal transaction");
                return requestResultAleks;

            case SEND_MONEY:
                // Kontrollime, kas saatjal on piisavalt raha
                if (!balanceServiceAleks.enoughMoneyOnAccount(balance, amount)) {
                    requestResultAleks.setAccountId(accountId);
                    requestResultAleks.setError("Insufficient funds " + amount);
                    return requestResultAleks;
                }

                // arvutame välja uue balance'i
                newBalance = balance - amount;

                // täidame ära saatja transactionDto
                transactionDtoAleks.setSenderAccountNumber(account.getAccountNumber());
                transactionDtoAleks.setBalance(newBalance);
                transactionDtoAleks.setLocalDateTime(LocalDateTime.now());
                transactionDtoAleks.setId(transactionId);

                //lisame tehingu transactionite alla (pluss inkrementeerime)
                bankAleks.addTransactionToTransactions(transactionDtoAleks);
                bankAleks.incrementTransactionId();

                // uuendame konto balance'it
                account.setBalance(newBalance);

                // meisterdame valmis result objekti
                requestResultAleks.setTransactionId(transactionId);
                requestResultAleks.setAccountId(accountId);
                requestResultAleks.setMessage("Successfully sent money");

                // teeme SAAJA transaktsiooni (JSON sõnumist ReceiverAccountNumber)
                receiverAccountNumber = transactionDtoAleks.getReceiverAccountNumber();

                // kontrollime, kas saaja konto number eksisteerib meie andmebaasis (bank)
                // kui on meie klient, teeme ka tema kontole laekumise transactioni
                if (accountServiceAleks.accountNumberExists(accounts, receiverAccountNumber)) {
                    // küsime konto nr
                    AccountDtoAleks receiverAccount = accountServiceAleks.getAccountByNumber(accounts, receiverAccountNumber);
                    int receiverNewBalance = receiverAccount.getBalance() + amount;

                    //loome uue transactioni objekti
                    TransactionDtoAleks receiverTransactionDtoAleks = new TransactionDtoAleks();

                    // täidame ära transactionDto
                    receiverTransactionDtoAleks.setSenderAccountNumber(account.getAccountNumber());
                    receiverTransactionDtoAleks.setReceiverAccountNumber(receiverAccountNumber);
                    receiverTransactionDtoAleks.setBalance(receiverNewBalance);
                    receiverTransactionDtoAleks.setLocalDateTime(LocalDateTime.now());
                    receiverTransactionDtoAleks.setId(bankAleks.getTransactionIdCount());
                    receiverTransactionDtoAleks.setAmount(amount);
                    receiverTransactionDtoAleks.setTransactionType(RECEIVE_MONEY);

                    // lisame tehingu transactionite alla (pluss inkrement)
                    bankAleks.addTransactionToTransactions(receiverTransactionDtoAleks);
                    bankAleks.incrementTransactionId();
                    receiverAccount.setBalance(receiverNewBalance);
                }

                return requestResultAleks;


            default:
                requestResultAleks.setError("Unknown transaction type: " + transactionType);
                return requestResultAleks;
        }

    }

    public RequestResultAleks receiveNewTransaction(BankAleks bankAleks, TransactionDtoAleks transactionDtoAleks) {
        RequestResultAleks requestResultAleks = new RequestResultAleks();
        String receiverAccountNumber = transactionDtoAleks.getReceiverAccountNumber();
        List<AccountDtoAleks> accounts = bankAleks.getAccounts();

        if (!accountServiceAleks.accountNumberExists(accounts, receiverAccountNumber)) {
            requestResultAleks.setError("No such account in our bank: " + receiverAccountNumber);
            return requestResultAleks;
        }

        AccountDtoAleks receiverAccount = accountServiceAleks.getAccountByNumber(accounts, receiverAccountNumber);
        int transactionId = bankAleks.getTransactionIdCount();
        int receiverNewBalance = receiverAccount.getBalance() + transactionDtoAleks.getAmount();

        transactionDtoAleks.setTransactionType(RECEIVE_MONEY);
        transactionDtoAleks.setBalance(receiverNewBalance);
        transactionDtoAleks.setId(transactionId);
        transactionDtoAleks.setAccountId(receiverAccount.getId());
        transactionDtoAleks.setLocalDateTime(LocalDateTime.now());

        bankAleks.addTransactionToTransactions(transactionDtoAleks);
        bankAleks.incrementTransactionId();

        receiverAccount.setBalance(receiverNewBalance);

        requestResultAleks.setTransactionId(transactionId);
        requestResultAleks.setMessage("Transaction received");

        return requestResultAleks;
    }




    // TODO:    createTransactionForNewAccount()
    //  account number
    //  balance 0
    //  amount 0
    //  transactionType 'n'
    //  receiver jääb null
    //  sender jääb null


}
