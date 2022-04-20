package fixel.cs.dto;

import fixel.cs.entity.AttachedFile;
import fixel.cs.entity.UpdateRecord;
import fixel.cs.entity.User;
import fixel.cs.type.Level;
import fixel.cs.type.ProjectType;
import fixel.cs.type.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqRegisterRequest {

    private String title;
    private User director;
    private List<Long> relatedUserNos;
    private String content;
    private List<AttachedFile> fileList;
    private UpdateRecord updateRecord;
    private boolean checked;
    private ProjectType projectType;
    private RequestType requestType;
    private Level level;

}
