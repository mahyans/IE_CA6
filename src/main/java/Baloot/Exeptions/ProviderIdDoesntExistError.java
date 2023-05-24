package Baloot.Exeptions;

public class ProviderIdDoesntExistError extends Exception{
    public ProviderIdDoesntExistError(String code) {
        super("ProviderIdDoesntExistError " +
                "Provider With ID " +
                code +
                " Not Found."
        );
    }
}
