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

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqUpdateRequest {

    // 변경에 있어서 꼭 필요한 부분만 바꿀 수 있어야 한다.
    // 초기 기획에서 벗어나지 않아야 한다.

    private String title;
    private Long dirUserNo;
    private List<Long> relatedUserNos;
    private String content;
    private List<AttachedFile> fileList;
    private ProjectType projectType;
    private RequestType requestType;
    private StatusCd statusCd;
    private Level level;
}
