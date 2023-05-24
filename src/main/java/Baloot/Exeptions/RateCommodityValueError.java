package Baloot.Exeptions;

public class RateCommodityValueError extends Exception{
    public RateCommodityValueError(String code) {
        super("Insufficient Rating Please Rate From [0-10]"
                + "Forbidden!"
        );
    }
}
