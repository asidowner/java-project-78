package hexlet.code;

import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class MapSchema extends BaseSchema {
    private Boolean isRequired = Boolean.FALSE;
    private Integer minSize;


    public MapSchema required() {
        this.isRequired = Boolean.TRUE;
        return this;
    }

    public MapSchema sizeof(int size) {
        this.minSize = size;
        return this;
    }

    @Override
    boolean isValid(Object object) {
        if (object == null) {
            return !isRequired;
        }

        if (!(object instanceof Map<?, ?>)) {
            return false;
        }

        Map<?, ?> map = (Map<?, ?>) object;

        if (minSize != null) {
            return map.size() >= minSize;
        }

        return true;
    }
}
