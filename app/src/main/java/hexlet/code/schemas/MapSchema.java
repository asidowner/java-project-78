package hexlet.code.schemas;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class MapSchema extends BaseSchema {
    private Boolean isRequired = Boolean.FALSE;
    private Integer minSize;
    private Map<String, BaseSchema> schemas = new HashMap<>();


    public final MapSchema required() {
        this.isRequired = Boolean.TRUE;
        return this;
    }

    public final MapSchema sizeof(int size) {
        this.minSize = size;
        return this;
    }

    public final MapSchema shape(Map<String, BaseSchema> shapeSchemas) {
        this.schemas.clear();
        this.schemas.putAll(shapeSchemas);
        return this;
    }

    @Override
    public final boolean isValid(Object object) {
        if (object == null) {
            return !isRequired;
        }

        if (!checkInstance(object)) {
            return false;
        }

        Map<?, ?> map = (Map<?, ?>) object;
        return checkMinSize(map) && checkBySchemas(map);
    }

    private boolean checkInstance(Object object) {
        return (object instanceof Map<?, ?>);
    }

    private boolean checkMinSize(Map<?, ?> map) {
        return !(minSize != null && map.size() < minSize);
    }

    private boolean checkBySchemas(Map<?, ?> map) {
        for (Map.Entry<String, BaseSchema> schemaEntry : schemas.entrySet()) {
            if (!schemaEntry.getValue().isValid(map.get(schemaEntry.getKey()))) {
                return false;
            }
        }
        return true;
    }
}
