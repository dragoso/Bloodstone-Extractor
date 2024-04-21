package he4ty.extractor;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ActionByPosition {
    @Builder.Default
    private List<SpriteFullDTO> spriteFullDTOS = new ArrayList<>();
    private int id;
    private int v8Mais24;
    private DirectionEnum directionEnum;
    private ActionEnum actionEnum;
}
