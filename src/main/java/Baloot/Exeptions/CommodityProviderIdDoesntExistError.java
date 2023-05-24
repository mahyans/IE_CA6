package Baloot.Exeptions;
public class CommodityProviderIdDoesntExistError extends Exception{
    public CommodityProviderIdDoesntExistError(String code) {
        super("CommodityProviderIdDoesntExistError " +
                        "Provider With ID " +
                        code +
                        " Not Found."
                );
    }
}
