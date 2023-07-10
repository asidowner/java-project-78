package hexlet.code.schemas;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class BaseSchema<T, S extends BaseSchema<?, ?>> {
    private Boolean isRequired = Boolean.FALSE;
    protected Map<String, Predicate<T>> checks = new HashMap<>();

    public BaseSchema() {
        addCheck("isInstanceOf", Objects::nonNull);
    }

    protected final void addCheck(String key, Predicate<T> predicate) {
        checks.put(key, predicate);
    }

    protected final void setIsRequired() {
        this.isRequired = Boolean.TRUE;
    }

    public final boolean isValid(Object object) {
        if (object == null) {
            return !isRequired;
        }

        try {
            return checks.values().stream()
                    .allMatch(predicate -> predicate.test((T) object));
        } catch (ClassCastException e) {
            return false;
        }
    }
}
