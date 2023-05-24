
import Baloot.Domain.Comment.CommentInit;
import Baloot.Domain.Product.CommodityInit;
import Baloot.Domain.User.Customer.UserInit;
import Baloot.Domain.User.Provider.ProviderInit;
import Baloot.System.BalootManager;
import com.google.gson.reflect.TypeToken;


        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.LinkedHashMap;
        import java.util.Map;

public class Main {
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
        }
    };

    private  static final  Map<String, Object> urls = new LinkedHashMap<>() {
        {
            put("http://5.253.25.110:5000/api/users", new TypeToken<ArrayList<UserInit>>() {
            }.getType());
            put("http://5.253.25.110:5000/api/providers", new TypeToken<ArrayList<ProviderInit>>() {
            }.getType());
            put("http://5.253.25.110:5000/api/commodities", new TypeToken<ArrayList<CommodityInit>>() {
            }.getType());
            put("http://5.253.25.110:5000/api/comments", new TypeToken<ArrayList<CommentInit>>() {
            }.getType());
        }
    };

    private static final String USERS_URL = "http://5.253.25.110:5000/api/users";
    private static final String COMMODITIES_URL = "http://5.253.25.110:5000/api/commodities";
    private static final String PROVIDERS_URL = "http://5.253.25.110:5000/api/providers";
    private static final String COMMENTS_URL = "http://5.253.25.110:5000/api/comments";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        System.out.println("IE Pro");
        BalootManager balootManager = new BalootManager();
    }
}
