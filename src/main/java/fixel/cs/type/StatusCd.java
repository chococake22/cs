package fixel.cs.type;

import lombok.Getter;

import java.util.Arrays;


@Getter
public enum StatusCd {

    OPEN(101, "요청 접수"),
    PROGRESS(102, "해결중"),
    CHECK(103, "검토"),
    REOPEN(104, "재검토"),
    CLOSE(105, "해결완료")
    ;

    private final Integer code;
    private final String message;

    StatusCd(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 요청별 상태 코드 반환
    public static StatusCd ofCode(Integer code) {
        return Arrays.stream(values())
                .filter(v -> v.getCode().intValue() == code.intValue())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("상태 코드 : " + code));
    }
}
