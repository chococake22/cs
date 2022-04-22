package fixel.cs.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TokenUserInfo {

    String uid;
    String authId;
}
