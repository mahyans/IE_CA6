package Baloot.Exeptions;
public class BalootDuplicateCommodityError extends Exception{
    public BalootDuplicateCommodityError(String code) {
        super("BalootDuplicateCommodityError " +
                        "Forbidden!" +
                        "Can't Modify Same commodities, " +
                        code +
                        " already exist."
                );
    }
}
