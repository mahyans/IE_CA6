package Baloot.Exeptions;

public class RateCommodityProviderIdDoesntExistError extends Exception{
    public RateCommodityProviderIdDoesntExistError(String code) {
        super("RateCommodityProviderIdDoesntExistError " +
                "Commodity Provider With ID " +
                code +
                " Not Found."
        );
    }
}
