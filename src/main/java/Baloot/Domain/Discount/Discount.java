package Baloot.Domain.Discount;

import java.util.HashMap;
import java.util.Map;

public class Discount {

    private String discountCode;
    private Double discount;

    private Map<String, Boolean> expired = new HashMap<>(); // key: userId, value: (false: not used by user, true: is used)


    public Discount(
            String discountCode_,
            Double discount_) {
        this.discountCode = discountCode_;
        this.discount = discount_;
    }

    public Boolean isExpired(String id) {
        return expired.get(id);
    }

    public void setExpired(String id, Boolean value) {
        this.expired.put(id, value);
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public Double getDiscount() {
        return discount;
    }
}
