package Baloot.Exeptions;
public class BuyListUserIdDoesntExistError extends Exception{
    public BuyListUserIdDoesntExistError(String code) {
        super("BuyListUserIdDoesntExistError " +
                "User With ID " +
                code +
                " Not Found."
        );
    }
}
