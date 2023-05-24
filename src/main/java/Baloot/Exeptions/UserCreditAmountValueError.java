package Baloot.Exeptions;

public class UserCreditAmountValueError extends Exception{
    public UserCreditAmountValueError(String code) {
        super("UserCreditAmountValueError: Insufficient Value Please Add More Than 0."
                + "\nForbidden!"
        );
    }
}
