package he4ty.extractor;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class SpriteFullDTO {

    @Builder.Default
    private List<SpritePartDTO> horizontais = new ArrayList<>();
}
