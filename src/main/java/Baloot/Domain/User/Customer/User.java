package Baloot.Domain.User.Customer;

import java.util.*;

public class User {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;

    private Double credit;
    private Boolean isSearched;
    private Boolean isSorted;

    private Map<String, Integer> buyListId = new HashMap<>(); // key: commodityId, value: -

    private List<String> searchId = new ArrayList<>(); // commodityId

    private String discount;
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Boolean getSorted() {
        return isSorted;
    }

    public void setSorted(Boolean sorted) {
        isSorted = sorted;
    }

    public List<String> getSearchId() {
        return searchId;
    }

    public void setSearchId(List<String> searchId) {
        this.searchId = searchId;
    }

    public Boolean getSearched() {
        return isSearched;
    }

    public void setSearched(Boolean searched) {
        isSearched = searched;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getBuyListId() {
        return buyListId;
    }

    public void setBuyListId(Map<String, Integer> buyListId) {
        this.buyListId = buyListId;
        this.price = 0.0;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public Double getCredit() {
        return credit;
    }

    public String getPassword() {
        return password;
    }

    public User(String username_,
                String password_,
                String email_,
                String birthDate_,
                String address_,
                Double credit_) {
        this.username = username_;
        this.password = password_;
        this.email = email_;
        this.birthDate = birthDate_;
        this.address = address_;
        this.credit = credit_;
        this.isSearched = false;
        this.isSorted = false;
        this.discount = "";
        this.price = 0.0;
    }

    public boolean isCommodityChosen(String comId){
        return this.buyListId.containsKey(comId);
    }

    public boolean isBudgetEnough(Double cost, Double discount) {
        Double decrease = (cost*discount)/100;
        cost -= decrease;
        if(credit - cost >= 0) {
            credit = (credit - cost);
            return true;
        }
        return false;
    }

    public void increaseWalletSize(Double cost) {
        credit += cost;
    }

    public void addCommodityToBuyList(String comId, Double price_){
        this.buyListId.put(comId, 0);
        this.price += price_;
    }

    public void removeCommodityFromBuyList(String comId, Double price_){
        Set<String> set = new HashSet<>();
        set.add(comId);
        buyListId.keySet().removeAll(set);
        this.price -= price_;

    }

}
