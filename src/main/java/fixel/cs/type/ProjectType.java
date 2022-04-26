package fixel.cs.type;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ProjectType {

    APL(301, "APL")
    ;

    private final Integer code;
    private final String message;

    ProjectType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 프로젝트 타입 코드 반환
    public static ProjectType ofCode(Integer code) {
        return Arrays.stream(values())
                .filter(v -> v.getCode().intValue() == code.intValue())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("상태 코드 : " + code));
    }
}
