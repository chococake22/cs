package fixel.cs.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "User")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long no;

    // 회원이메일(아이디)
    private String userEmail;

    // 비밀번호
    private String password;

    // 회원이름
    private String username;

    // 비밀번호 변경여부(y/n)
    private String pwdChangedYn;
}
