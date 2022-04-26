package fixel.cs.entity;

import fixel.cs.dto.request.QuesUpdateRequest;
import fixel.cs.type.Level;
import fixel.cs.type.ProjectType;
import fixel.cs.type.RequestType;
import fixel.cs.type.StatusCd;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

import java.util.List;

@Entity(name = "Question")
@Table(name = "Question")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question {

    // 문의번호
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question")
    private Long questionNo; // db와 바로 매치될 수 있게 카멜로 변경

    // 제목
    private String title;

    // 작성자
    private Long regUserNo;

    // 담당자
    private Long dirUserNo;

    // 문의별관련자
    @ElementCollection
    private List<Long> relatedUserNos;

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

    public void update(QuesUpdateRequest request) {
        this.dirUserNo = request.getDirUserNo();
        this.relatedUserNos = request.getRelatedUserNos();
        this.content = request.getContent();
        this.projectType = request.getProjectType();
        this.statusCd = request.getStatusCd();
        this.requestType = request.getRequestType();
        this.level = request.getLevel();
    }
}
