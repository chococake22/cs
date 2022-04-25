package fixel.cs.entity;

import fixel.cs.dto.request.ReqUpdateRequest;
import fixel.cs.type.Level;
import fixel.cs.type.ProjectType;
import fixel.cs.type.RequestType;
import fixel.cs.type.StatusCd;

import fixel.cs.util.LongToConverter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Request")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Request {

    // 문의번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_no")
    private Long no;

    // 제목
    private String title;

    // 작성자
    private Long regUserNo;

    // 담당자
    private Long dirUserNo;

    // 문의별관련자
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Long> relatedUserNos = new ArrayList<>();

    // 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 프로젝트 항목
    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    // 상태 코드
    @Enumerated(EnumType.STRING)
    private StatusCd statusCd;

    // 요청타입
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    // 중요도
    @Enumerated(EnumType.STRING)
    private Level level;

    // 등록일자
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regDt;

    public void update(ReqUpdateRequest request) {
        this.dirUserNo = request.getDirUserNo();
        this.relatedUserNos = request.getRelatedUserNos();
        this.content = request.getContent();
        this.projectType = request.getProjectType();
        this.statusCd = request.getStatusCd();
        this.requestType = request.getRequestType();
        this.level = request.getLevel();
    }
}
