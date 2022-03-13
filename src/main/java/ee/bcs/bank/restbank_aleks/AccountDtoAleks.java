package ee.bcs.bank.restbank_aleks;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDtoAleks {

    private int id;
    private String accountNumber;
    private String firstName;
    private String lastName;
    private Integer balance;
    private Boolean locked;

}
