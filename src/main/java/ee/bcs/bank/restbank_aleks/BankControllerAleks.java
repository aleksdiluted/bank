package ee.bcs.bank.restbank_aleks;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/solution")
public class BankControllerAleks {
    public static BankAleks bankAleks = new BankAleks();

    @Resource  // klassi sees, kuid väljaspool meetodeid
    private AccountServiceAleks accountServiceAleks;

    @Resource
    private TransactionServiceAleks transactionServiceAleks;

    @Resource // autowire
    private BankServiceAleks bankServiceAleks;

    @Resource
    private AccountDtoAleks accountDtoAleks;

    @GetMapping("/bank") // endpoint
    public BankAleks getBank() {
        return bankAleks;
    }

    @GetMapping("/example/account")
    public AccountDtoAleks getExampleAccount() {
        return accountServiceAleks.createExampleAccount();
    }

    @GetMapping("/example/transaction")
    public TransactionDtoAleks getExampleTransaction() {
        return transactionServiceAleks.createExampleTransaction();
    }

    @PostMapping("/new/account")  // Post loob või lisab juurde midagi
    public RequestResultAleks addAccountToBank(@RequestBody AccountDtoAleks accountDto) {
        return bankServiceAleks.addAccountToBank(bankAleks, accountDto);
    }

    @PostMapping("/new/transaction")
    public RequestResultAleks addNewTransaction(@RequestBody TransactionDtoAleks transactionDtoAleks) {
        return transactionServiceAleks.addNewTransaction(bankAleks, transactionDtoAleks);
    }

    @PostMapping("/receive/transaction")
    public RequestResultAleks receiveNewTransaction(@RequestBody TransactionDtoAleks transactionDtoAleks) {
        return transactionServiceAleks.receiveNewTransaction(bankAleks, transactionDtoAleks);
    }

    @PutMapping("/update/owner")
    public RequestResultAleks updateOwnerDetails(@RequestBody AccountDtoAleks accountDto) {
        return accountServiceAleks.updateOwnerDetails(bankAleks.getAccounts(), accountDto);
    }

    @DeleteMapping("/delete/account")
    public RequestResultAleks deleteAccount(@RequestParam int accountId) {
        return accountServiceAleks.deleteAccount(bankAleks.getAccounts(), accountId);
    }

    // todo: tee endpoint, millega saab kontot lukustada/avada. Kontrolige ka ID olemasolu

    @PutMapping("/lock/account")
    public RequestResultAleks lockAccount(@RequestParam int accountId) {
        return accountServiceAleks.lockAccount(bankAleks.getAccounts(), accountId);
    }

//    @GetMapping("/bankstatement/by/lastname")
//    public RequestResultAleks bankStatement(TransactionDtoAleks transactionDtoAleks) {
//        transactionServiceAleks.getBankStatement(bankAleks.getAccounts(), accountDtoAleks.getLastName());
//
//        return RequestResultAleks;
//    }

 /*List<String> accountNumbers = new ArrayList<>();
        accountNumbers.add("EE123");
        accountNumbers.add("EE456");
        accountNumbers.add("EE999");
        accountNumbers.add("GB123");
        accountNumbers.add("GB456");
        accountNumbers.add("GB999");

        List<String> result = new ArrayList<>();

        // teeme uue tühja listi TransactionDto-dest ja sinna lisame juurde
        // add()-iga vaid need TransactionDto, mis kuuluvad omanikule.

        for (String accountNumber : accountNumbers) {
            if (accountNumbers.contains("EE")) {
                result.add(accountNumber);
            }
        }*/
    // todo: loo endpoint /bankstatement/by/lastname
    //  teenus (konto väljavõte) peab tagastama Listi TransactionDTO-dest,
    //  mis kuulub omanikule (by last name)
    //  teeme tühja listi ja hakkame sinna sisse välju kirjutama
    //  Arrays.asList(new int []{1, 2, 3, 4, 5, 6});
    //  Kes tahab, võib teha uue teenuse BankStatementService.





    // TODO: et saada bank objektist ülevaade, siis loo uus controlleri endpoint    /bank
    //  meetodi nimeks pane                                                         getBank()
    // 25.01.22

    // TODO: et saada üks accounts JSON'i näidis,
    //  siis loo uus controlleri endpoint                                           /example/account
    //  meetodi nimeks pane                                                         getExampleAccount()
    //  loo accountService alla uus teenus                                          createExampleAccount()

    // TODO: et saada üks transaction JSON'i näidis,
    //  siis loo uus controlleri endpoint                                           /example/transaction
    //  meetodi nimeks pane                                                         getExampleTransaction()
    //  loo transactionService alla uus teenus                                      createExampleTransaction()

    // TODO: Et lisada uus account, loo uus controlleri endpoint                    /new/account
    //  võta RequestBodyst sisse accountDto objekt
    //  loo bankService alla uus teenus                                             addAccountToBank()
    //  ja lisa see konto bank accounts listi
    //  teenus võiks tagastada RequestResult objekti koos koos loodava konto id ja transaktsiooni id'ga


    //  loo transactionService alla uus teenus                                      createTransactionForNewAccount()
    //  loo bankService alla uus teenus                                             addTransaction()


}
