package Baloot.Exeptions;

public class AlreadyAddedToBuyList extends Exception{
    public AlreadyAddedToBuyList(String code) {
        super("AlreadyAddedToBuyList " +
                        "Forbidden!" +
                        "Can't Modify Same commodities, " +
                        code +
                        " already exist in buy list."
                );
    }
}
