package Baloot.Domain.Product;

public class FilterCommodity {
    private String id;

    public FilterCommodity(String commodityId) {
        this.id = commodityId;
    }

    public String getCommodityId() {
        return id;
    }
}
