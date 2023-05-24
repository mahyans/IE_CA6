package Baloot.Exeptions;
public class CommodityUserMailDoesntExistError extends Exception{
    public CommodityUserMailDoesntExistError(String code) {
        super("CommodityUserMailDoesntExistError " +
                        "Provider With ID " +
                        code +
                        " Not Found."
                );
    }
}
