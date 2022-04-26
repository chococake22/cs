package fixel.cs.dto.request;

import fixel.cs.type.Level;
import fixel.cs.type.ProjectType;
import fixel.cs.type.RequestType;
import fixel.cs.type.StatusCd;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqRegisterRequest {

    @NotEmpty
    @ApiModelProperty(example = "댓글 작성이 안돼요")
    private String title;

    @NotEmpty
    @ApiModelProperty(example = "3")
    private Long mainUserNo;  // 타입, 변수명 변경 -> 명확한 데이터 정보 반영해서 작성하기

    @ApiModelProperty(example = "[2, 5]")
    private List<Long> relatedUserNos;

    @NotEmpty
    @ApiModelProperty(example = "댓글 등록 버튼이 안됩니다.")
    private String content;

    // 첨부파일 목록 추가

    @NotEmpty
    @ApiModelProperty(example = "APL")
    private ProjectType projectType;

    @NotEmpty
    @ApiModelProperty(example = "CODE_REVIEW")
    private RequestType requestType;

    @NotEmpty
    @ApiModelProperty(example = "400")
    private StatusCd statusCd;

    @NotEmpty
    @ApiModelProperty(example = "EMERGENCY")
    private Level level;

    // 날짜 불필요
    // private LocalDateTime regDt;

}
