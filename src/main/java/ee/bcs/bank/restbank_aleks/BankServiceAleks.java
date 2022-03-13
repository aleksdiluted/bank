package ee.bcs.bank.restbank_aleks;

import org.springframework.stereotype.Service;

@Service
public class BankServiceAleks {

    // TODO: loo teenus addAccountToBank() mis lisab uue konto bank accounts'i alla
    //  enne seda võta bank alt järgmine account id ja lisa see ka kontole
    //  ära unusta siis pärast seda accountIdCount'id suurendada
    public RequestResultAleks addAccountToBank(BankAleks bankAleks, AccountDtoAleks accountDto) {
        int accountId = bankAleks.getAccountIdCount();
        accountDto.setId(accountId);
        accountDto.setBalance(0); // see rida paneb loodava konto jäägi nulliks, et keegi ei saaks endale luua 10M kontot.
        accountDto.setLocked(false);
        bankAleks.addAccountToAccounts(accountDto);
        bankAleks.incrementAccountId(); // suurendab ID-d ühe võrra.

        RequestResultAleks requestResultAleks = new RequestResultAleks();
        requestResultAleks.setAccountId(accountDto.getId());
        // TODO: kontrolli, kas kontonr on juba olemas. Kui jah, lisa vastav error. Võib mõelda
        requestResultAleks.setMessage("Added new account");
        return requestResultAleks;
    }


    // TODO: loo teenus addTransaction() mis lisab uue tehingu bank transactions'i alla
    //  enne seda võta bank alt järgmine transactionIdCount id ja lisa see ka tehingule
    //  ära unusta siis pärast seda transactionIdCount'id suurendada

}
