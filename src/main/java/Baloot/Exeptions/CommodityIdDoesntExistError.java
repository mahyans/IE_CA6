package Baloot.Exeptions;
public class CommodityIdDoesntExistError extends Exception{
    public CommodityIdDoesntExistError(String code) {
        super("CommodityIdDoesntExistError " +
                "Commodity With ID " +
                code +
                " Not Found."
        );
    }
}
