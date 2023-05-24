package Controller;

import Baloot.Baloot;
import Baloot.Domain.User.Customer.User;
import Baloot.Domain.User.Customer.UserInit;
import Baloot.Domain.User.Customer.UserServices;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@RestController
@RequestMapping("/login")
public class Login {
    @GetMapping("")
    public void login(final HttpServletResponse response) throws Exception {
        if(Baloot.getInstance().getOnlineClient() != null)
            response.sendRedirect("http://localhost:8080/");
    }
    public static final String OK_Customer_LoggedIn = "Customer Logged in Successfully!";

    @PostMapping("")
    public String login(@RequestBody String jsonString, final HttpServletResponse response) throws Exception {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(jsonString, Properties.class);
        try {
            if(!UserServices.getInstance().checkUserExist(properties.getProperty("username"), properties.getProperty("password")))
                response.sendRedirect("http://localhost:8080/login");
            else{
                UserServices.getInstance().setClientOnline(properties.getProperty("username"));
//                response.sendRedirect("http://localhost:8080/");
                return OK_Customer_LoggedIn;
            }
            return null;
        } catch (Exception e) {
//            e.printStackTrace();
            Baloot.getInstance().setErrorMessage(e.getMessage());
//            response.sendRedirect("http://localhost:8080/error");
            response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
            return null;
        }
    }

}
