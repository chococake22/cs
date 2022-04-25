package fixel.cs.dto.request;

import fixel.cs.entity.AttachedFile;
import fixel.cs.entity.User;
import fixel.cs.type.Level;
import fixel.cs.type.ProjectType;
import fixel.cs.type.RequestType;
import fixel.cs.type.StatusCd;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqReadRequest {

    private String title;   // 제목
    private Long dirUserNo;  // 담당자
    private List<Long> relatedUserNos;  // 관련자
    private String content; // 내용
    private List<AttachedFile> fileList;    // 첨부파일
    private ProjectType projectType;    // 프로젝트 타입
    private StatusCd statusCd;
    private RequestType requestType;    // 요청 항목
    private Level level;    // 중요도
    private LocalDateTime regDt;    // 작성일자

}
