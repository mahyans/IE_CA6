package Baloot.Exeptions;

public class UserPasswordDoesntMatchError extends Exception{
    public UserPasswordDoesntMatchError(String code) {
        super("UserPasswordDoesntMatchError " +
                "Password Of User With ID " +
                code +
                " Doesnt Match."
        );
    }
}
