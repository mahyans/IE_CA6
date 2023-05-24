package Baloot.Exeptions;
public class DoesntExistInBuyList extends Exception{
    public DoesntExistInBuyList(String code) {
        super("DoesntExistInBuyList " +
                        "Commodity With Id " +
                        code +
                        " Doesnt Exist In Buy List."
                );
    }
}
