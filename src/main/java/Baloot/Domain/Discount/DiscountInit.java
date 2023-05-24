package Baloot.Domain.Discount;

public class DiscountInit {

    private String discountCode;
    private Double discount;

    public DiscountInit(
            String discountCode_,
            Double discount_) {
        this.discountCode = discountCode_;
        this.discount = discount_;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public Double getDiscount() {
        return discount;
    }
}
