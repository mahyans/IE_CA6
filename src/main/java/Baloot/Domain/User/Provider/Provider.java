package Baloot.Domain.User.Provider;

public class Provider {
    private String name;
    private String registryDate;
    private Integer id;

    public String getName() {
        return name;
    }

    public String getId() {
        return Integer.toString(id);
    }

    public String getRegistryDate() {
        return registryDate;
    }

    public Provider(String name_,
                    String registryDate_,
                    Integer id_) {
        this.name = name_;
        this.registryDate = registryDate_;
        this.id = id_;
    }
}
