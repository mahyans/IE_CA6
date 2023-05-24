package Baloot.Domain.Product;


import Baloot.Domain.Comment.Comment;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commodity implements Comparable<Commodity> {
    private String name;
    private ArrayList<String> categories;
    private Integer id;
    private Integer providerId;
    private Double price;
    private Double rating;
    private Integer inStock;
    private Map<String, RateCommodity> ratings = new HashMap<>(); // key: userId, value: score
    private Double sum;
    private Double average;
    private Map<Integer, Comment>  comments = new HashMap<>();
    private Integer cmCounter;

    private String providerName;

    private List<String> recommendId; // commodityId

    private Double score;


    public Commodity(String name_,
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
        this.rating = rating_;
        this.inStock = inStock_;
//        this.ratings.put("", new RateCommodity("",0,0));
        this.sum = rating_;
        this.average = rating_;
        RateCommodity rc = new RateCommodity("-1", id_, 0);
        ratings.put(rc.getUsername(), rc);
        sum = sum + rc.getScore();
        average = sum / ratings.size();
        this.cmCounter = 0;
        this.recommendId = new ArrayList<>();
        this.score = 0.0;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<String> getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(List<String> recommendId) {
        this.recommendId = recommendId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public String getCategoriesString(String splitter) {
        String catsString = "{";
        for (int i = 0; i < categories.size(); i++) {
            if (i > 0)
                catsString += splitter;
            catsString += categories.get(i);
        }
        catsString += "}";
        return catsString;
    }
    public Double getPrice() {
        return price;
    }

    public Double getRating() {
        return average;
    }

    public Integer getInStock() {
        return inStock;
    }

    public Map<String, RateCommodity> getRatings() {
        return ratings;
    }

    public boolean isInStock() {
        return inStock > 0;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderId() {
        return Integer.toString(providerId);
    }

    public String getId() {
        return Integer.toString(id);
    }

    public void addToRating(RateCommodity rc) {
        if (ratings.containsKey(rc.getUsername()))
        {
            sum = sum - ratings.get(rc.getUsername()).getScore();
            sum = sum + rc.getScore();
            average = sum / ratings.size();
            ratings.put(rc.getUsername(), rc);
            return;
        }

        ratings.put(rc.getUsername(), rc);
        sum = sum + rc.getScore();
        average = sum / ratings.size();
    }

    public Map<Integer, Comment> getCommodityComments(){
        return comments;
    }

    public void addToComments(Comment c){
        comments.put(cmCounter++, c);
    }

    public void updateCommentLikes(Integer idx, Integer isLike){
        if (isLike.equals(1))
            comments.get(idx).setLikeCount();
        else if (isLike.equals(-1))
            comments.get(idx).setDislikeCount();
    }

    public boolean isCat(String cat) {
        for (String comC : categories)
            if (comC.equals(cat))
                return true;
        return false;
    }

    public boolean isInSpan(Double fromPrice, Double toPrice){
        if (price >= fromPrice && price <= toPrice)
            return true;
        return false;
    }

    private JSONObject similarToJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("name", name);
        obj.put("price", price);
        obj.put("categories", categories);
        obj.put("rating", average);
        return obj;
    }

    public JSONObject exposeToJSON() {
        JSONObject obj = new JSONObject();
        obj = similarToJson();
        obj.put("providerId", providerId);
        obj.put("inStock", inStock);
        return obj;
    }


    public JSONObject exposeSingleToJSON() {
        JSONObject obj = new JSONObject();
        obj = similarToJson();
        obj.put("provider", providerName);
        return obj;
    }

    @Override
    public int compareTo(Commodity o) {
        return this.getPrice().compareTo(o.getPrice());
    }
}
