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
public class QuesRegisterRequest {

    private String title;
    private Long director;
    private List<Long> relatedUserNos;
    private String content;
    private ProjectType projectType;
    private RequestType requestType;
    private StatusCd statusCd;
    private Level level;
    private LocalDateTime regDt;

}
