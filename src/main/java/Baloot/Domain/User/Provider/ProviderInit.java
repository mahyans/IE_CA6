package Baloot.Domain.User.Provider;

public class ProviderInit {
    private String name;
    private String registryDate;
    private Integer id;

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public ProviderInit(String name_,
                        String registryDate_,
                        Integer id_) {
        this.name = name_;
        this.registryDate = registryDate_;
        this.id = id_;
    }
}
