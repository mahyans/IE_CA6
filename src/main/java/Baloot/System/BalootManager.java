package Baloot.System;

import Baloot.Baloot;
import Baloot.Domain.User.*;
import Baloot.Domain.Discount.Discount;
import Baloot.Domain.Discount.DiscountInit;
import Baloot.Domain.Product.*;
import Baloot.Domain.Comment.Comment;
import Baloot.Domain.Comment.CommentInit;
import Baloot.Domain.User.Customer.User;
import Baloot.Domain.User.Customer.UserInit;
import Baloot.Domain.User.Provider.Provider;
import Baloot.Domain.User.Provider.ProviderInit;
import Baloot.Exeptions.*;
import InterFace.HTMLHandler;
import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class BalootManager {
    private static final Integer MAX_RATING_SPAN = 11;
    private static final Integer MIN_RATING_SPAN = 0;

    private Map<String, User> users = new HashMap<>();
    private Map<String, Provider> providers = new HashMap<>();
    private Map<Integer, Comment> allComments = new HashMap<>();
    private Map<String, Commodity> commodities = new HashMap<>();
    private Map<String, Discount> discounts = new HashMap<>();
    private Map<String, Map<String, String>> commodityId = new HashMap<>(); // key username key comment id value commodity id

    private HTMLHandler HTMLHandler = new HTMLHandler();

    private String readResourceFile(String fileName) throws Exception {
        File file = new File(Resources.getResource("templates/" + fileName).toURI());
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

    public String addUser(UserInit userI_) throws Exception {
        User user_ = new User(userI_.getUsername(), userI_.getPassword(), userI_.getEmail(), userI_.getBirthDate(),
                userI_.getAddress(), userI_.getCredit());

        return "User successfully added.";
    }

    public String addProvider(ProviderInit providerI_) throws Exception {
        Provider provider_ = new Provider(providerI_.getName(), providerI_.getRegistryDate(), providerI_.getId());
        return "Provider successfully added.";
    }

    public String addCommodity(CommodityInit commodityI_) throws Exception {
        Commodity commodity_ = new Commodity(commodityI_.getName(), commodityI_.getCategories(), commodityI_.getProviderId(),
                commodityI_.getPrice(), commodityI_.getRating(), commodityI_.getInStock(), commodityI_.getId());

        commodity_.setProviderName(providers.get(commodity_.getProviderId()).getName());
        commodities.put(commodity_.getId(), commodity_);
        return "Commodity successfully added.";
    }

    public String addComment(User user, String comId, String txt) throws Exception {
        String username = doesUserMailExist(user.getEmail());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String date = (dtf.format(now));
        Comment comment_ = new Comment(user.getEmail(), username, Integer.valueOf(comId), txt, date);
        Commodity c = commodities.get(comId);
        c.addToComments(comment_);
        allComments.put(allComments.size(), comment_);
        return "Comment successfully added.";
    }

    public String addComment(CommentInit commentI_) throws Exception {
        String username = doesUserMailExist(commentI_.getUserEmail());

        Comment comment_ = new Comment(commentI_.getUserEmail(), username, commentI_.getCommodityId(), commentI_.getText(), commentI_.getDate());
        Commodity c = commodities.get(Integer.toString(commentI_.getCommodityId()));
        c.addToComments(comment_);
        allComments.put(allComments.size(), comment_);
        return "Comment successfully added.";
    }

    public String addDiscount(DiscountInit discountI_) throws Exception {
        Discount discount = new Discount(discountI_.getDiscountCode(), discountI_.getDiscount());
        for (User u :users.values())
            discount.setExpired(u.getUsername(), false);
        discounts.put(discountI_.getDiscountCode(), discount);
        return "Discount successfully added.";
    }

    public void saveCommodityVotedId(String username, String commentIdx, String commodityId_){
        Map<String, String> m = new HashMap<String, String>();
        m.put(commentIdx, commodityId_);
        commodityId.put(username, m);
    }

    public String getCommodityVotedId(String username, String commentIdx){
        return commodityId.get(username).get(commentIdx);
    }

    private String doesUserMailExist(String mail){
        for (User user : users.values())
            if (user.getEmail().equals(mail)){
                return user.getUsername();
            }
        return "";
    }

    public List<Commodity> getCommodities() throws Exception {
        List<Commodity> commoditiesList = new ArrayList<Commodity>(commodities.values());
//        Collections.sort(commoditiesList);
        return commoditiesList;
    }

    public List<Provider> getProviders() throws Exception {
        List<Provider> providersList = new ArrayList<Provider>(providers.values());
        return providersList;
    }

    public List<User> getUsers() throws Exception {
        List<User> usersList = new ArrayList<User>(users.values());

        return usersList;
    }

    public List<Comment> getComments() throws Exception {
        List<Comment> commentsList = new ArrayList<Comment>(allComments.values());

        return commentsList;
    }

    public String rateCommodity(String userId, String commId, Integer rate) throws Exception {
        if (users.containsKey(userId)) { // userId is username
            if (commodities.containsKey(commId)) {
                if (rate<MAX_RATING_SPAN && rate>MIN_RATING_SPAN) {
                    Commodity c = commodities.get(commId);
                    RateCommodity rm = new RateCommodity(userId, Integer.valueOf(commId), rate);
                    c.addToRating(rm);
                }else
                    throw new RateCommodityValueError("");
            } else
                throw new RateCommodityIdDoesntExistError(String.format(commId));
        }else
            throw new RateCommodityUserIdDoesntExistError(String.format(userId));
        return "Commodity successfully rated.";
    }
/*
    public String rateCommodity(RateCommodity commodity_) throws Exception {
        if (users.containsKey(commodity_.getUsername())) {
            if (commodities.containsKey(commodity_.getCommodityId())) {
                if (providers.containsKey(commodities.get(commodity_.getCommodityId()).getProviderId())) {
                    if (commodity_.getScore()<MAX_RATING_SPAN && commodity_.getScore()>MIN_RATING_SPAN) {
                        Commodity c = commodities.get(commodity_.getCommodityId());
                        c.addToRating(commodity_);
                    }else
                        throw new RateCommodityValueError("");
                } else
                    throw new RateCommodityProviderIdDoesntExistError(String.format(commodities.get(commodity_.getCommodityId()).getProviderId()));
            } else
                throw new RateCommodityIdDoesntExistError(String.format(commodity_.getCommodityId()));
        }else
            throw new RateCommodityUserIdDoesntExistError(String.format(commodity_.getUsername()));
        return "Commodity successfully rated.";
    }
*/
    public String addToBuyList(String username, Integer commodityId) throws Exception {
        if (users.containsKey(username)) {
            if (commodities.containsKey(Integer.toString(commodityId))) {
                if (commodities.get(Integer.toString(commodityId)).isInStock()) {
                    if (users.get(username).isCommodityChosen(Integer.toString(commodityId)))
                        throw new AlreadyAddedToBuyList(String.format(Integer.toString(commodityId)));
                    else
                        users.get(username).addCommodityToBuyList(Integer.toString(commodityId), commodities.get(Integer.toString(commodityId)).getPrice());
                } else
                    throw new NotEnoughCommodityError(String.format(Integer.toString(commodityId)));
            } else
                throw new CommodityIdDoesntExistError(String.format(Integer.toString(commodityId)));
        } else
            throw new BuyListUserIdDoesntExistError(String.format(username));
        return "Commodity successfully added to buy list.";
    }
/*
    public String addToBuyList(BuyList buyList_) throws Exception {
        if (users.containsKey(buyList_.getUsername())) {
            if (commodities.containsKey(Integer.toString(buyList_.getCommodityId()))) {
                if (commodities.get(Integer.toString(buyList_.getCommodityId())).isInStock()) {
                    if (users.get(buyList_.getUsername()).isCommodityChosen(Integer.toString(buyList_.getCommodityId())))
                        throw new AlreadyAddedToBuyList(String.format(Integer.toString(buyList_.getCommodityId())));
                    else
                        users.get(buyList_.getUsername()).addCommodityToBuyList(Integer.toString(buyList_.getCommodityId()), commodities.get(Integer.toString(buyList_.getCommodityId())).getPrice());
                } else
                    throw new NotEnoughCommodityError(String.format(Integer.toString(buyList_.getCommodityId())));
            } else
                throw new Exeptions.CommodityIdDoesntExistError(String.format(Integer.toString(buyList_.getCommodityId())));
        } else
            throw new Exeptions.BuyListUserIdDoesntExistError(String.format(buyList_.getUsername()));
        return "Commodity successfully added to buy list.";
    }
    */

/*
    public String addLikeToComments(String userId, String commoId, Integer commIdx, Integer isLiked) throws Exception {
        if (users.containsKey(userId)) { // userId is username
            if (commodities.containsKey(commoId)) {
                commodities.get(commoId).updateCommentLikes(commIdx, isLiked);
            } else
                throw new RateCommodityIdDoesntExistError(String.format(commoId));
        }else
            throw new RateCommodityUserIdDoesntExistError(String.format(userId));
        return "Commodity like comment successfully added.";
    }
*/
    public String removeFromBuyList(BuyList buyList_) throws Exception {
        if (users.containsKey(buyList_.getUsername())) {
            if (users.get(buyList_.getUsername()).isCommodityChosen(Integer.toString(buyList_.getCommodityId())))
                users.get(buyList_.getUsername()).removeCommodityFromBuyList(Integer.toString(buyList_.getCommodityId()), commodities.get(Integer.toString(buyList_.getCommodityId())).getPrice());
            else
                throw new DoesntExistInBuyList(String.format(Integer.toString(buyList_.getCommodityId())));
        } else
            throw new BuyListUserIdDoesntExistError(String.format(buyList_.getUsername()));
        return "Commodity successfully removed from buy list.";
    }
/*
    public String removeFromBuyList(String username, String commodityId) throws Exception {
        if (users.containsKey(username)) {
            if (users.get(username).isCommodityChosen(commodityId))
                users.get(username).removeCommodityFromBuyList(commodityId, commodities.get(commodityId).getPrice());
            else
                throw new DoesntExistInBuyList(String.format(commodityId));
        } else
            throw new BuyListUserIdDoesntExistError(String.format(username));
        return "Commodity successfully removed from buy list.";
    }
*/
    public Commodity getCommodity(String commodityId) throws Exception {
        if (!commodities.containsKey(commodityId))
            throw new CommodityIdDoesntExistError(commodityId);
        Commodity commodity = commodities.get(commodityId);
        return commodity;
    }

    public Provider getProvider(String providerID) throws Exception {
        if (!providers.containsKey(providerID))
            throw new ProviderIdDoesntExistError(providerID);
        Provider pro = providers.get(providerID);
        return pro;
    }

    public User getUser(String userID) throws Exception {
        User user = users.get(userID);
        return user;
    }

    public Boolean checkUserExist(String id, String pass) throws Exception {

        if (users.get(id).getPassword().equals(pass))
            return true;
        else
            throw new UserPasswordDoesntMatchError(id);
//        return false;
    }

    private List<Commodity> commoditiesCollection(String username){
        List<Commodity> commoditiesList = new ArrayList<>();
        if (users.get(username).getSearched()){
            for (String id : users.get(username).getSearchId())
                commoditiesList.add(commodities.get(id));
        }
        else
            for (Commodity com : commodities.values())
                commoditiesList.add(com);

        if (users.get(username).getSorted())
            Collections.sort(commoditiesList);

        return commoditiesList;
    }

    public String exposeRecommendations(Commodity com) throws Exception {
        String commodityHTML = "";
        for (String id : com.getRecommendId()) {
            String recommend = "";
            recommend = exposeCommodity(commodities.get(id), "CommoditiesItem.html");
            commodityHTML += recommend;
        }
        return commodityHTML;
    }

    private String voteCommentUp(String username, String commentIdx){
//        return "/voteComment/" + username + "/" + commentIdx + "/1";
        return "voteCommentUp/";
    }

    private String voteCommentDown(String username, String commentIdx){
//        return "/voteComment/" + username + "/" + commentIdx + "/-1";
        return "voteCommentDown/";
    }

    private String prepareCommentHtml(Integer cmKey, Comment cmVal, String commodityCommentItemHTML) throws Exception {
        HashMap<String, String> cmContext = new HashMap<>();
        cmContext.put("username", cmVal.getUsername());
        cmContext.put("comment", cmVal.getText());
        cmContext.put("date", cmVal.getDate());
        cmContext.put("likeVal", Integer.toString(cmVal.getLikeCount()));
        cmContext.put("dislikeVal", Integer.toString(cmVal.getDislikeCount()));
        cmContext.put("commentIDX", Integer.toString(cmKey));
        cmContext.put("votelikecm", voteCommentUp(cmVal.getUsername(), Integer.toString(cmKey)));
        cmContext.put("votedislikecm", voteCommentDown(cmVal.getUsername(), Integer.toString(cmKey)));
        String res = HTMLHandler.templateFill(commodityCommentItemHTML, cmContext);
        return res;
    }

    public String exposeComments(Commodity com) throws Exception {
        String commodityHTML = "";
        Map<Integer, Comment> commodities = com.getCommodityComments();
        String commodityCommentItemHTML = readResourceFile("CommodityCommentItem.html");
        for (Map.Entry<Integer, Comment> cm : commodities.entrySet()) {
            commodityHTML += prepareCommentHtml(cm.getKey(), cm.getValue(), commodityCommentItemHTML);
            saveCommodityVotedId(cm.getValue().getUsername(), Integer.toString(cm.getKey()), com.getId());
        }

        return commodityHTML;
    }

    private String prepareCommodityHtml(Commodity com, String commoditiesItemHTML) throws Exception {
        HashMap<String, String> context = new HashMap<>();
        context.put("id", com.getId());
        context.put("name", com.getName());
        context.put("providerId", com.getProviderId());
        context.put("price", Double.toString(com.getPrice()));
        context.put("categories", com.getCategoriesString(", "));
        context.put("rating", Double.toString(com.getRating()));
        context.put("inStock", Double.toString(com.getInStock()));
        String res = HTMLHandler.templateFill(commoditiesItemHTML, context);
        return res;
    }

    public String exposeCommodity(Commodity com, String type) throws Exception {
        String commoditiesHTML = "";
        String commoditiesItemHTML = readResourceFile(type);
        commoditiesHTML += prepareCommodityHtml(com, commoditiesItemHTML);
        return commoditiesHTML;
    }
    private String prepareBuyHtml(Commodity com, String buyHTML) throws Exception {
        HashMap<String, String> context = new HashMap<>();
        context.put("id", com.getId());
        context.put("name", com.getName());
        context.put("providerName", com.getProviderName());
        context.put("price", Double.toString(com.getPrice()));
        context.put("categories", com.getCategoriesString(", "));
        context.put("rating", Double.toString(com.getRating()));
        context.put("inStock", Double.toString(com.getInStock()));
        String res = HTMLHandler.templateFill(buyHTML, context);
        return res;
    }

    public String exposeBuyList(String username) throws Exception {
        User user = users.get(username);
        String userHTML = "";
        String buyHTML = readResourceFile("UserBuyList.html");
        for (String comId : user.getBuyListId().keySet()){
            userHTML += prepareBuyHtml(commodities.get(comId), buyHTML);
        }
        return userHTML;
    }

    private String prepareUserHtml(User user, String commoditiesItemHTML) throws Exception {
        HashMap<String, String> context = new HashMap<>();
        context.put("username", user.getUsername());
        context.put("email", user.getEmail());
        context.put("birthDate", user.getBirthDate());
        context.put("address", user.getAddress());
        context.put("credit", Double.toString(user.getCredit()));
        context.put("buy", Double.toString(user.getPrice()));
        String res = HTMLHandler.templateFill(commoditiesItemHTML, context);
        return res;
    }

    public String exposeUser(String username) throws Exception {
        User user = users.get(username);
        String userHTML = "";
        String userInfoHTML = readResourceFile("UserInfo.html");
        userHTML += prepareUserHtml(user, userInfoHTML);
        return userHTML;
    }

    public String exposeCommodities(String username) throws Exception {
        List<Commodity> commoditiesList = new ArrayList<>();
        commoditiesList = commoditiesCollection(username);

        String commoditiesHTML = "";
        String commoditiesItemHTML = readResourceFile("CommoditiesItem.html");
        for (Commodity com : commoditiesList) {
            commoditiesHTML += prepareCommodityHtml(com, commoditiesItemHTML);
        }
        return commoditiesHTML;
    }

    private void updateClearCommoditiesList(String username){
        List<String> commoditiesList = new ArrayList<>();
        users.get(username).setSearched(false);
        users.get(username).setSorted(false);
        users.get(username).setSearchId(commoditiesList);
    }

    private void updateSearchCommoditiesList(String username, String type, String input, List<Commodity> list){
        List<String> commoditiesList = new ArrayList<>();
        if (type.equals("searchByCategory"))
            commoditiesList = getCommoditiesId(input, list);
        else if (type.equals("searchByName"))
            commoditiesList = getContainedCommodities(input, list);

        users.get(username).setSearched(true);
        users.get(username).setSearchId(commoditiesList);
    }

    private void updateSortCommoditiesList(String username, List<Commodity> list){
        List<String> commoditiesList = new ArrayList<>();
        for (Commodity com : list)
            commoditiesList.add(com.getId());
        users.get(username).setSorted(true);
        users.get(username).setSearchId(commoditiesList);
    }

    private List<Commodity> recentCommoditiesList(String username){
        List<Commodity> commoditiesList = new ArrayList<>();
        if (!users.get(username).getSearched()) {
            for (Commodity com : commodities.values())
                commoditiesList.add(com);
        }
        else {
            for (String id : users.get(username).getSearchId())
                commoditiesList.add(commodities.get(id));
        }
        return commoditiesList;
    }

    public void collectCommodities(String username, String type, String input){
        List<Commodity> commoditiesList = recentCommoditiesList(username);
        switch (type){
            case "searchByName", "searchByCategory" -> updateSearchCommoditiesList(username, type, input, commoditiesList);
            case "clear" -> updateClearCommoditiesList(username);
            case "sortByPrice" -> updateSortCommoditiesList(username, commoditiesList);
        }
    }

    public List<Commodity> getCommodities(CategoryCommodity categoryCommodity) throws Exception {
        List<Commodity> commoditiesList = new ArrayList<Commodity>();
        for (Commodity com : commodities.values())
        {
            if (com.isCat(categoryCommodity.getCategory()))
                commoditiesList.add(com);
        }

//        Collections.sort(commoditiesList);
        return commoditiesList;
    }

    public List<String> getContainedCommodities(String name, List<Commodity> list){
        List<String> commoditiesList = new ArrayList<>();
        for (Commodity com : list)
        {
            if (com.getName().contains(name))
                commoditiesList.add(com.getId());
        }

//        Collections.sort(commoditiesList);
        return commoditiesList;
    }

    public List<String> getCommoditiesId(String categoryCommodity, List<Commodity> list){
        List<String> commoditiesList = new ArrayList<>();
        for (Commodity com : list)
        {
            if (com.isCat(categoryCommodity))
                commoditiesList.add(com.getId());
        }

//        Collections.sort(commoditiesList);
        return commoditiesList;
    }

    public List<Commodity> getCommodities(String categoryCommodity) throws Exception {
        List<Commodity> commoditiesList = new ArrayList<Commodity>();
        for (Commodity com : commodities.values())
        {
            if (com.isCat(categoryCommodity))
                commoditiesList.add(com);
        }

//        Collections.sort(commoditiesList);
        return commoditiesList;
    }

    public List<Commodity> getCommodities(Double fromPrice, Double toPrice) throws Exception {

        List<Commodity> commoditiesList = new ArrayList<Commodity>();
        for (Commodity com : commodities.values())
        {
            if (com.isInSpan(fromPrice, toPrice))
                commoditiesList.add(com);
        }

//        Collections.sort(commoditiesList);
        return commoditiesList;
    }

    public List<Commodity> getBuyList(WishList wishList) throws Exception {
        if (!users.containsKey(wishList.getUsername()))
            throw new BuyListUserIdDoesntExistError(String.format(wishList.getUsername()));

        List<Commodity> buyList = new ArrayList<Commodity>();
        for(String userCommodityId : users.get(wishList.getUsername()).getBuyListId().keySet()) {
            buyList.add(commodities.get(userCommodityId));
        }
        return buyList;
    }

    public String addCredit(Double amount, String username) throws Exception {

        users.get(username).increaseWalletSize(amount);

        return "Wallet Size Successfully Increased.";
    }

    public void findRecommendationItems(String comId){
        Commodity org =  commodities.get(comId);
        List<String> rec = org.getRecommendId();

        Integer coefficient = 11;
//        List<Commodity> commoditiesList = new ArrayList<Commodity>();
//        Collections.sort(commoditiesList);
        ArrayList<Commodity> commoditiesList = new ArrayList<>();

        for(Commodity com : commodities.values()){
            com.setScore(0.0);
            if(comId.equals(com.getId()))
                continue;

            Integer present = 0;
            for(String cat : com.getCategories())
                if(org.isCat(cat))
                    present = 1;
            com.setScore(coefficient*present + com.getRating());
            commoditiesList.add(com);
        }
        commoditiesList.sort(Comparator.comparing(Commodity::getScore).reversed());

        for (int i = 0; i < 5; i++) {
            rec.add(commoditiesList.get(i).getId());
        }
    }

    public String doPurchase(String username) throws Exception {
        User user = users.get(username);
        String code = user.getDiscount();
        Double discount = 0.0;
        if (code != "")
            discount = discounts.get(code).getDiscount();
        if (user.isBudgetEnough(user.getPrice(), discount)) {
            if (code != "") {
                user.setDiscount("");
                discounts.get(code).setExpired(username, true);
            }
            Map<String, Integer> empty = new HashMap<>();
            user.setBuyListId(empty);
            return "Thanks For Your Purchase!";
        }
        else
            throw new UserCreditAmountError("");

    }

    public void userHaveDiscountCode(String username, String code) throws Exception {

        discounts.get(code).setExpired(username, false);
        users.get(username).setDiscount(code);
    }
}

