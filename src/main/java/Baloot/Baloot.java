package Baloot;

import Baloot.Domain.User.*;
import Baloot.Domain.Discount.DiscountInit;
import Baloot.Domain.Product.*;
import Baloot.Domain.Comment.Comment;
import Baloot.Domain.Comment.CommentInit;
import Baloot.Domain.User.Customer.User;
import Baloot.Domain.User.Customer.UserInit;
import Baloot.Domain.User.Provider.Provider;
import Baloot.Domain.User.Provider.ProviderInit;
import Baloot.System.BalootManager;
import InterFace.HTTPRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.*;

public class Baloot {
    private static final Map<String, Object> DB = new LinkedHashMap<>() {
        {
            put("src\\test\\java\\resources\\Users.json", new TypeToken<ArrayList<UserInit>>() {
            }.getType());
            put("src\\test\\java\\resources\\Providers.json", new TypeToken<ArrayList<ProviderInit>>() {
            }.getType());
            put("src\\test\\java\\resources\\Commodities.json", new TypeToken<ArrayList<CommodityInit>>() {
            }.getType());
            put("src\\test\\java\\resources\\Comments.json", new TypeToken<ArrayList<CommentInit>>() {
            }.getType());
            put("src\\test\\java\\resources\\Discounts.json", new TypeToken<ArrayList<DiscountInit>>() {
            }.getType());
        }
    };

    private static final Map<String, Object> urls = new LinkedHashMap<>() {
        {
            put("http://5.253.25.110:5000/api/users", new TypeToken<ArrayList<UserInit>>() {
            }.getType());
            put("http://5.253.25.110:5000/api/providers", new TypeToken<ArrayList<ProviderInit>>() {
            }.getType());
            put("http://5.253.25.110:5000/api/commodities", new TypeToken<ArrayList<CommodityInit>>() {
            }.getType());
            put("http://5.253.25.110:5000/api/comments", new TypeToken<ArrayList<CommentInit>>() {
            }.getType());
            put("http://5.253.25.110:5000/api/discount", new TypeToken<ArrayList<DiscountInit>>() {
            }.getType());
        }
    };
    private static final int PORT = 8080;
    private User onlineClient = null;
    private Commodity selectedCommodity = null;

    private BalootManager balootManager = new BalootManager();

    private static String errorMessage;

    public String getErrorMessage(){
        return errorMessage;
    }
    public void setErrorMessage(String error){
        errorMessage = error;
    }

    private static Baloot instance;

    public static Baloot getInstance() {
        if (instance == null) {
            instance = new Baloot();

            try {
//                setupDBBase(DB);
                setupAPIBase(urls);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public static void setupAPIBase(Map<String, Object> URLs) {
        try {
            for (Map.Entry<String, Object> url : URLs.entrySet()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonString = HTTPRequest.getRequest(url.getKey());
                ArrayList<Object> objects = gson.fromJson(jsonString, (Type) url.getValue());

                for (Object o : objects) {
                    if (o.getClass() == UserInit.class)
                        Baloot.getInstance().addUser((UserInit) o);
                    if (o.getClass() == ProviderInit.class)
                        Baloot.getInstance().addProvider((ProviderInit) o);
                    if (o.getClass() == CommodityInit.class)
                        Baloot.getInstance().addCommodity((CommodityInit) o);
                    if (o.getClass() == CommentInit.class)
                        Baloot.getInstance().addComment((CommentInit) o);
                    if (o.getClass() == DiscountInit.class)
                        Baloot.getInstance().addDiscount((DiscountInit) o);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void setupDBBase(Map<String, Object> jsons) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (Map.Entry<String, Object> json : jsons.entrySet()) {
                File jsonFile = new File(json.getKey()).getAbsoluteFile();
                String jsonString = FileUtils.readFileToString(jsonFile);
                ArrayList<Object> objects = gson.fromJson(jsonString, (Type) json.getValue());
                for (Object o : objects) {
                    if (o.getClass() == UserInit.class)
                        Baloot.getInstance().addUser((UserInit) o);
                    if (o.getClass() == ProviderInit.class)
                        Baloot.getInstance().addProvider((ProviderInit) o);
                    if (o.getClass() == CommodityInit.class)
                        Baloot.getInstance().addCommodity((CommodityInit) o);
                    if (o.getClass() == CommentInit.class)
                        Baloot.getInstance().addComment((CommentInit) o);
                    if (o.getClass() == DiscountInit.class)
                        Baloot.getInstance().addDiscount((DiscountInit) o);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User getOnlineClient() throws Exception {
        return onlineClient;
    }

    private void setClientOnline(String username) throws Exception {
        onlineClient = balootManager.getUser(username);
    }

    public void setUnselectCommodity() throws Exception {
        selectedCommodity = null;
    }

    public Commodity getSelectedCommodity() throws Exception {
        return selectedCommodity;
    }

    public void setSelectCommodity(String comId) throws Exception {
        selectedCommodity = balootManager.getCommodity(comId);
    }

    public void setClientOffline() throws Exception {
        onlineClient = null;
    }


    public String htmlOfCommodities() throws Exception {
        return balootManager.exposeCommodities(onlineClient.getUsername());
    }

    public String htmlOfCommodity() throws Exception {
        return balootManager.exposeCommodity(selectedCommodity, "CommodityItemBegin.html");
    }

    public String htmlOfComments() throws Exception {
        return balootManager.exposeComments(selectedCommodity);
    }

    public String htmlOfUser() throws Exception {
        return balootManager.exposeUser(onlineClient.getUsername());
    }

    public String htmlOfBuyList() throws Exception {
        return balootManager.exposeBuyList(onlineClient.getUsername());
    }

    public String htmlOfCommodityRecommendation() throws Exception {
        List<String> fresh = new ArrayList<>();
        selectedCommodity.setRecommendId(fresh);
        balootManager.findRecommendationItems(selectedCommodity.getId());
        return balootManager.exposeRecommendations(selectedCommodity);
    }

    public void doPurchase() throws Exception {
        balootManager.doPurchase(onlineClient.getUsername());
    }

    public void userHaveDiscountCode(String code) throws Exception {
        balootManager.userHaveDiscountCode(onlineClient.getUsername(), code);
    }

    public void setCommoditiesCollections(String type, String input){
        balootManager.collectCommodities(onlineClient.getUsername(), type, input);
    }

    public String addCredit(Double amount) throws Exception {
        return balootManager.addCredit(amount, onlineClient.getUsername());
    }

    public Boolean clientCanLogin(String username, String pass) throws Exception {
        Boolean res = balootManager.checkUserExist(username, pass);
        if (res)
            setClientOnline(username);
        return res;
    }

    public String addUser(UserInit userI_) throws Exception {
        return balootManager.addUser(userI_);
    }

    public String addProvider(ProviderInit providerI_) throws Exception {
        return balootManager.addProvider(providerI_);
    }

    public String addCommodity(CommodityInit commodityI_) throws Exception {
        return balootManager.addCommodity(commodityI_);
    }

    public String addComment(String txt) throws Exception {
        return balootManager.addComment(onlineClient, selectedCommodity.getId(), txt);
    }

    public String addComment(CommentInit commentI_) throws Exception {
        return balootManager.addComment(commentI_);
    }

    public String addDiscount(DiscountInit discountI_) throws Exception {
        return balootManager.addDiscount(discountI_);
    }

    public void saveCommodityVotedId(String username, String commentIdx, String commodityId_) {
        balootManager.saveCommodityVotedId(username, commentIdx, commodityId_);
    }

    public String getCommodityVotedId(String username, String commentIdx) {
        return balootManager.getCommodityVotedId(username, commentIdx);
    }

    public List<Commodity> getCommodities() throws Exception {
        return balootManager.getCommodities();
    }

    public List<Provider> getProviders() throws Exception {
        return balootManager.getProviders();
    }

    public List<User> getUsers() throws Exception {
        return balootManager.getUsers();
    }

    public List<Comment> getComments() throws Exception {
        return balootManager.getComments();
    }

    public String rateCommodity(String rate) throws Exception {
        return balootManager.rateCommodity(onlineClient.getUsername(), selectedCommodity.getId(), Integer.valueOf(rate));
    }


    public String addToBuyList() throws Exception {
        return balootManager.addToBuyList(onlineClient.getUsername(), Integer.valueOf(selectedCommodity.getId()));
    }


/*
    public String addLikeToComments(String commIdx, Integer isLiked) throws Exception {
        return balootManager.addLikeToComments(onlineClient.getUsername(), selectedCommodity.getId(), Integer.valueOf(commIdx), isLiked);
    }
*/
    public String removeFromBuyList(BuyList buyList_) throws Exception {
        return balootManager.removeFromBuyList(buyList_);
    }
/*
    public String removeFromBuyList(String commodityId) throws Exception {
        return balootManager.removeFromBuyList(onlineClient.getUsername(), commodityId);
    }
*/
    public Commodity getCommodity(String commodityId) throws Exception {
        return balootManager.getCommodity(commodityId);
    }

    public Provider getProvider(String providerID) throws Exception {
        return balootManager.getProvider(providerID);
    }

    public User getUser(String userID) throws Exception {
        return balootManager.getUser(userID);
    }

    public List<Commodity> getCommodities(CategoryCommodity categoryCommodity) throws Exception {
        return balootManager.getCommodities(categoryCommodity);
    }

    public List<Commodity> getCommodities(String categoryCommodity) throws Exception {
        return balootManager.getCommodities(categoryCommodity);
    }

    public List<Commodity> getCommodities(Double fromPrice, Double toPrice) throws Exception {
        return balootManager.getCommodities(fromPrice, toPrice);
    }

    public List<Commodity> getBuyList(WishList wishList) throws Exception {
        return balootManager.getBuyList(wishList);
    }
}

