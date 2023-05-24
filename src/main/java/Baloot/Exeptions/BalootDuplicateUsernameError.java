package Baloot.Exeptions;
public class BalootDuplicateUsernameError extends Exception{
    public BalootDuplicateUsernameError(String code) {
        super("BalootDuplicateUsernameError " +
                        "Forbidden!" +
                        "Can't Modify Same Usernames, " +
                        code +
                        " already exist."
                );
    }
}
