package fixel.cs.entity;

import fixel.cs.type.Level;
import lombok.*;

import javax.persistence.*;

@Entity(name = "SendEmailHistory")
@Table(name = "SendEmailHistory")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SendEmailHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sendmailhistory_no")
    private Long sendMailHistoryNo;

    private String userEmail;

    private String sendEmailDt;

    private String sendType;

    private Level level;

    private Long requestNo;
}
