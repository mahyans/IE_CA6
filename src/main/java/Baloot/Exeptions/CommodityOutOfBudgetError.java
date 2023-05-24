package Baloot.Exeptions;
public class CommodityOutOfBudgetError extends Exception{
    public CommodityOutOfBudgetError(String code) {
        super("CommodityOutOfBudgetError " +
                "Forbidden!" +
                "Not Enough User Budget " +
                code +
                " ."
        );
    }
}
