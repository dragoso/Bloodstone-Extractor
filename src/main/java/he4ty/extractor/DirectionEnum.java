package he4ty.extractor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DirectionEnum {

    NON(0),
    UP(1),
    RIGTH(2),
    DOWN(3),
    LEFT(4);
    private final int value;

    public static DirectionEnum valueOf(int value) {
        return Arrays.stream(DirectionEnum.values()).filter(v -> v.value == value).findFirst().orElse(NON);
    }
}
