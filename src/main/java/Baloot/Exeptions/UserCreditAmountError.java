package Baloot.Exeptions;

public class UserCreditAmountError extends Exception{
    public UserCreditAmountError(String code) {
        super("UserCreditAmountError: Not Enough Cash! "
                + "\nForbidden!"
        );
    }
}
