package Controller;

import Baloot.Baloot;
import Baloot.Domain.User.Customer.UserServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/logout")
public class Logout {
    public static final String OK_RESPONSE = "OK!";

    @GetMapping("")
    public void logout(final HttpServletResponse response) throws Exception {
        if(Baloot.getInstance().getOnlineClient() != null)
            UserServices.getInstance().setClientOnline(null);
        response.sendRedirect("http://localhost:8080/");
    }

    @PostMapping("")
    public String logout(@RequestBody String jsonString, final HttpServletResponse response) throws Exception {
        return OK_RESPONSE;
    }
}