package fixel.cs.type;

import lombok.Getter;

@Getter
public enum StatusCd {

    OPEN(1000, "요청 접수"),
    PROGRESS(2000, "해결중"),
    CHECK(3000, "검토"),
    REOPEN(4000, "재검토"),
    CLOSE(5000, "해결완료")
    ;

    private final Integer code;
    private final String message;

    StatusCd(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
