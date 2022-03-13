package ee.bcs.bank.restbank_aleks;

import org.springframework.stereotype.Service;

@Service
public class BalanceServiceAleks {

    public boolean enoughMoneyOnAccount(Integer balance, Integer amount) {
        return balance >= amount;  // kui on true, siis on true
        }
}
