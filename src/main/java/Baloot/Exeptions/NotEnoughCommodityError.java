package Baloot.Exeptions;

public class NotEnoughCommodityError extends Exception{
    public NotEnoughCommodityError(String code) {
        super("NotEnoughCommodityError " +
                "Forbidden!" +
                "End Of Inventory With Code"+
                code +
                " ."
        );
    }
}
