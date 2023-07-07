package hexlet.code;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class MapSchema extends BaseSchema {
    private Boolean isRequired = Boolean.FALSE;
    private Integer minSize;
    private Map<String, BaseSchema> schemas = new HashMap<>();


    public MapSchema required() {
        this.isRequired = Boolean.TRUE;
        return this;
    }

    public MapSchema sizeof(int size) {
        this.minSize = size;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> shapeSchemas) {
        this.schemas.clear();
        this.schemas.putAll(shapeSchemas);
        return this;
    }

    @Override
    public boolean isValid(Object object) {
        if (object == null) {
            return !isRequired;
        }

        if (!(object instanceof Map<?, ?>)) {
            return false;
        }

        Map<String, Object> map = (Map<String, Object>) object;

        if (minSize != null && map.size() < minSize) {
            return false;
        }

        if (!schemas.isEmpty()) {
            return checkBySchemas(map);
        }
        return true;
    }

    private boolean checkBySchemas(Map<String, ?> map) {
        for (Map.Entry<String, BaseSchema> schemaEntry : schemas.entrySet()) {
            if (!schemaEntry.getValue().isValid(map.get(schemaEntry.getKey()))) {
                return false;
            }
        }
        return true;
    }
}
