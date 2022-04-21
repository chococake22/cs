package fixel.cs.dto.user;


import lombok.Data;

@Data
public class UserAddRequest {

    private String userEmail;
    private String password;
    private String username;

}
