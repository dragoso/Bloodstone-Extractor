package he4ty.extractor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ActionEnum {

    NON(0),
    ATTACK(65),
    DEAD(68),
    STAND(83),
    WALKING(87);
    private final int value;

    public static ActionEnum valueOf(int value) {
        return Arrays.stream(ActionEnum.values()).filter(v -> v.value == value).findFirst().orElse(NON);
    }
}
