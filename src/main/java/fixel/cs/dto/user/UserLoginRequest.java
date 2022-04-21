package fixel.cs.dto.user;


import lombok.Data;

@Data
public class UserLoginRequest {

    private String userEmail;
    private String password;

}
