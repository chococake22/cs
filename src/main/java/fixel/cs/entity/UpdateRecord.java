package fixel.cs.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "UpdateRecord")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateRecord {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "updaterecord_no")
    private Long no;

    // 이전 담당자
    private Long beforeDirectorUserNo;

    // 현재 담당자
    private Long targetDirectorUserNo;

    // 변경날짜
    private LocalDateTime updateDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_no")
    private Request request;
}
