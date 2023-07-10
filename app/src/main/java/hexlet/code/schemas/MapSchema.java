package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public class MapSchema extends BaseSchema<Map<String, Object>, MapSchema> {
    public final MapSchema required() {
        super.setIsRequired();
        return this;
    }

    public final MapSchema sizeof(int size) {
        Predicate<Map<String, Object>> predicate = map -> map.size() >= size;
        super.addCheck("isMoreThanMinSize", predicate);
        return this;
    }

    public final MapSchema shape(Map<String, BaseSchema> shapeSchemas) {
        Predicate<Map<String, Object>> predicate = map -> shapeSchemas.entrySet().stream()
                .allMatch(entry -> {
                    if (!map.containsKey(entry.getKey())) {
                        return false;
                    }
                    return entry.getValue().isValid(map.get(entry.getKey()));
                });
        super.addCheck("isVerifyBySchema", predicate);
        return this;
    }
}
