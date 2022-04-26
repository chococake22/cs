package fixel.cs.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Comment")
@Table(name = "Comment")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo;

    // 작성자
    private Long regUserNo;

    // 내용
    private String content;

    // 작성일자
    private LocalDateTime regDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_no")
    private Question question;
}
