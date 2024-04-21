package he4ty.extractor;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class SpriteDTO {
    private Integer id;
    private Integer type;
    @Builder.Default
    private List<ActionByPosition> actionByPositions = new ArrayList<>();

}
