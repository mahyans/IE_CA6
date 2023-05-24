package Baloot.Domain.Product;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommodityInit {
    private String name;
    private ArrayList<String> categories;
    private Integer id;
    private Integer providerId;
    private Double price;
    private Double rating;
    private Integer inStock;

    public CommodityInit(String name_,
                         ArrayList<String> categories_,
                         Integer providerId_,
                         Double price_,
                         Double rating_,
                         Integer inStock_,
                         Integer id_) {
        this.name = name_;
        this.categories = categories_;
        this.id = id_;
        this.providerId = providerId_;
        this.price = price_;
        this.rating = 0.0;
        this.inStock = inStock_;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public Double getPrice() {
        return price;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getInStock() {
        return inStock;
    }
}
