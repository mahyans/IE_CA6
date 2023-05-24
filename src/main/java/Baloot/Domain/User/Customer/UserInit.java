package Baloot.Domain.User.Customer;

public class UserInit {
    private String username;
    private String password;
    private String email;
    private String birthDate;
    private String address;
    private Double credit;

    public UserInit(String username_,
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
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
}
