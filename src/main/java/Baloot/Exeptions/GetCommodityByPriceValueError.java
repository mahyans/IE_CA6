package Baloot.Exeptions;

public class GetCommodityByPriceValueError extends Exception{
    public GetCommodityByPriceValueError() {
        super("Insufficient Span Please Choose From >0"
                + "Forbidden!"
        );
    }
}
