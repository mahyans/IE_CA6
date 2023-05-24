package Baloot.Exeptions;

public class RateCommodityUserIdDoesntExistError extends Exception{
    public RateCommodityUserIdDoesntExistError(String code) {
        super("RateCommodityUserIdDoesntExistError " +
                "User With ID " +
                code +
                " Not Found."
        );
    }
}
