package Baloot.Domain.Product;

public class BuyList {
    private String username;
    private Integer commodityId;

    public BuyList(
            String username,
            Integer commodityId
    )
    {
        this.username = username;
        this.commodityId = commodityId;
    }

    public String getUsername() {
        return username;
    }

    public Integer getCommodityId() {
        return commodityId;
    }
}
