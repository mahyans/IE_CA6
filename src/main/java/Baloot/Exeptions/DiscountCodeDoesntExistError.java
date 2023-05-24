package Baloot.Exeptions;

public class DiscountCodeDoesntExistError extends Exception{
    public DiscountCodeDoesntExistError(String code) {
        super("DiscountCodeDoesntExistError " +
                        "Discount With Code " +
                        code +
                        " Not Found."
                );
    }
}
