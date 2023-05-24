package Baloot.Exeptions;

public class UserIdDoesntExistError extends Exception{
    public UserIdDoesntExistError(String code) {
        super("UserIdDoesntExistError " +
                "User With ID " +
                code +
                " Not Found."
        );
    }
}
