package fixel.cs.type;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RequestType {

    ERROR_CORRECTION(200, "에러 검수"),
    NEW_DEVELOPMENT(201, "신규 기능 추가"),
    REQUEST_DATA(202, "데이터 요청"),
    REQUEST_AUTHENTICATION(203, "접근 권한"),
    CODE_REVIEW(204, "코드 리뷰");

    private final Integer code;
    private final String message;

    RequestType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 요청 타입 코드 반환
    public static RequestType requestType(Integer code) {
        return Arrays.stream(values())
                .filter(v -> v.getCode().intValue() == code.intValue())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("요청 타입 : " + code));
    }
}
