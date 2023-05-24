package Baloot.Domain.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RateCommodity {
    String username;
    Integer commodityId;
    Integer score;

    public String getUsername() {
        return username;
    }

    public String getCommodityId() {
        return Integer.toString(commodityId);
    }

    public Integer getScore() {
        return score;
    }

    public RateCommodity(String username,
                         Integer commodityId,
                         Integer score
    )
    {
        this.username = username;
        this.commodityId = commodityId;
        this.score = score;
    }
}
