package ee.bcs.bank.restbank;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/solution")
public class BankController {
    public static Bank bank = new Bank();

    @Resource  // klassi sees, kuid väljaspool meetodeid
    private AccountService accountService;

    @Resource
    private TransactionService transactionService;

    @Resource // autowire
    private BankService bankService;

    @GetMapping("/bank") // endpoint
    public Bank getBank() {
        return bank;
    }

    @GetMapping("/example/account")
    public AccountDto getExampleAccount() {
        return accountService.createExampleAccount();
    }

    @GetMapping("/example/transaction")
    public TransactionDto getExampleTransaction() {
        return transactionService.createExampleTransaction();
    }

    @PostMapping("/new/account")  // Post loob või lisab juurde midagi
    public RequestResult addAccountToBank(@RequestBody AccountDto accountDto) {
        return bankService.addAccountToBank(bank, accountDto);
    }

    @PostMapping("/new/transaction")
    public RequestResult addNewTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.addNewTransaction(bank, transactionDto);
    }

    @PostMapping("/receive/transaction")
    public RequestResult receiveNewTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.receiveNewTransaction(bank, transactionDto);
    }

    @PutMapping("/update/owner")
    public RequestResult updateOwnerDetails(@RequestBody AccountDto accountDto) {
        return accountService.updateOwnerDetails(bank.getAccounts(), accountDto);
    }

    // todo: tee endpoint, millega saab kontot lukustada/avada. Kontrolige ka ID olemasolu

    @DeleteMapping("/delete/account")
    public RequestResult deleteAccount(@RequestParam int accountId) {
        return accountService.deleteAccount(bank.getAccounts(), accountId);
    }


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
