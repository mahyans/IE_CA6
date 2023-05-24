package Baloot.Exeptions;
public class BalootDuplicateProviderError extends Exception{
    public BalootDuplicateProviderError(String code) {
        super("BalootDuplicateProviderError " +
                        "Forbidden!" +
                        "Can't Modify Same providers, " +
                        code +
                        " already exist."
                );
    }
}
