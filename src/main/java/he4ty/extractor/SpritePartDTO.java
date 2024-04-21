package he4ty.extractor;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class SpritePartDTO {

    @Builder.Default
    private List<Integer> sprite = new ArrayList<>();

    @Builder.Default
    private List<byte[]> buffer = new ArrayList<>();
}
