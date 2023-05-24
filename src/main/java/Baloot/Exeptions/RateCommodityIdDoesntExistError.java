package Baloot.Exeptions;

public class RateCommodityIdDoesntExistError extends Exception{
    public RateCommodityIdDoesntExistError(String code) {
        super("RateCommodityIdDoesntExistError " +
                "Commodity With ID " +
                code +
                " Not Found."
        );
    }
}
