package Baloot.Exeptions;

public class DiscountCodeExpiredError extends Exception{
    public DiscountCodeExpiredError(String code) {
        super("DiscountCodeExpiredError " +
                        "Discount With Code " +
                        code +
                        " Has Already Been Used, It Is Expired."
                );
    }
}
