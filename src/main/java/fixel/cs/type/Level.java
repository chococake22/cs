package fixel.cs.type;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Level {

    EMERGENCY(400, "긴급"),
    HIGH(401, "상"),
    MIDDLE(402, "중"),
    LOW(403, "하")
    ;

    private final Integer code;
    private final String message;

    Level(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Level requestType(Integer code) {
        return Arrays.stream(values())
                .filter(v -> v.getCode().intValue() == code.intValue())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("요청 타입 : " + code));
    }
}
