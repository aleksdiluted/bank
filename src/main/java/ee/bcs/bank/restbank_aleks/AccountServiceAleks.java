package ee.bcs.bank.restbank_aleks;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service // Springi annotatsioon - Spring ise ühendab ära.
public class AccountServiceAleks {

    // TODO: loo teenus createExampleAccount() mis loob uue AccountDto objekti:
    //  account number = random account number
    //  firstName "John"
    //  lastName "Smith"
    //  balance 0
    //  locked false

    public AccountDtoAleks createExampleAccount() {
        AccountDtoAleks accountDto = new AccountDtoAleks();
        accountDto.setAccountNumber(createRandomAccountNumber());
        accountDto.setFirstName("Juss");
        accountDto.setLastName("Sepp");
        accountDto.setBalance(0);
        accountDto.setLocked(false);
        return accountDto;
    }


    private String createRandomAccountNumber() {
        //  Creates random account number between EE1000 -  EE9999
        Random random = new Random();
        return "EE" + (random.nextInt(9999) + 1000);
    }

    public boolean accountIdExists(List<AccountDtoAleks> accounts, int accountId) {
        for (AccountDtoAleks account : accounts) {
            if (account.getId() == accountId) {
                return true;
            }
        }
        return false;
    }

    public AccountDtoAleks getAccountById(List<AccountDtoAleks> accounts, int accountId) {
        // käime läbi kõik kontod 'accounts' Listis
        //  ja paneme iga konto muutujasse 'account'
        for (AccountDtoAleks account : accounts) {
            // kui leiame konto, mille id on võrdne accountId-ga
            if (account.getId() == accountId) {
                // siis tagastame selle konto
                return account;
            }
        }
        return null;
    }

    public boolean accountNumberExists(List<AccountDtoAleks> accounts, String receiverAccountNumber) {
        for (AccountDtoAleks account : accounts) {
            if (account.getAccountNumber().equals(receiverAccountNumber)) {
                return true;
            }
        }
        return false;
    }

    public AccountDtoAleks getAccountByNumber(List<AccountDtoAleks> accounts, String receiverAccountNumber) {
        for (AccountDtoAleks account : accounts) {
            if (account.getAccountNumber().equals(receiverAccountNumber)) {
                return account;
            }
        }
        return null;
    }

    public RequestResultAleks updateOwnerDetails(List<AccountDtoAleks> accounts, AccountDtoAleks accountDtoAleks) {
        RequestResultAleks requestResultAleks = new RequestResultAleks();

        int accountId = accountDtoAleks.getId();
        if (!accountIdExists(accounts, accountId)) {
            requestResultAleks.setError("Account ID: " + accountId + " does not exist!");
            requestResultAleks.setAccountId(accountId);
            return requestResultAleks;
        }

        AccountDtoAleks account = getAccountById(accounts, accountId);
        account.setFirstName(accountDtoAleks.getFirstName());
        account.setLastName(accountDtoAleks.getLastName());

        requestResultAleks.setAccountId(accountId);
        requestResultAleks.setMessage("Successfully updated account");

        return requestResultAleks;
    }

    public RequestResultAleks deleteAccount(List<AccountDtoAleks> accounts, int accountId) {
        RequestResultAleks requestResultAleks = new RequestResultAleks();

        if (!accountIdExists(accounts, accountId)) {
            requestResultAleks.setError("Account ID: " + accountId + " does not exist!");
            requestResultAleks.setAccountId(accountId);
            return requestResultAleks;
        }

        AccountDtoAleks account = getAccountById(accounts, accountId);
        accounts.remove(account);

        requestResultAleks.setMessage("Account deleted");
        requestResultAleks.setAccountId(accountId);

        return requestResultAleks;
    }

    public RequestResultAleks lockAccount(List<AccountDtoAleks> accounts, int accountId) {
        RequestResultAleks requestResultAleks = new RequestResultAleks();

        if (!accountIdExists(accounts, accountId)) {
            requestResultAleks.setError("Account ID: " + accountId + " does not exist!");
            requestResultAleks.setAccountId(accountId);
            return requestResultAleks;
        }

        AccountDtoAleks account = getAccountById(accounts, accountId);
        account.setLocked(!account.getLocked());
        requestResultAleks.setAccountId(accountId);

        if (account.getLocked()) {
            requestResultAleks.setMessage("Account ID: " + accountId + " is locked!");
        } else {
            requestResultAleks.setMessage("Account ID: " + accountId + " is unlocked!");
        }
        return requestResultAleks;
    }
}
