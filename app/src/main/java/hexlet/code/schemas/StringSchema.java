package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public class StringSchema extends BaseSchema<String, StringSchema> {

    public StringSchema() {
        addCheck("isInstanceOf", Objects::nonNull);
    }

    public final StringSchema required() {
        super.setIsRequired();
        addCheck("isNotEmpty", string -> !string.equals(""));
        return this;
    }

    public final StringSchema minLength(int minLength) {
        if (minLength < 0) {
            throw new RuntimeException("The minimum length must be greater than zero");
        }
        addCheck("minSize", string -> string.length() >= minLength);
        return this;
    }

    public final StringSchema contains(String contained) {
        Predicate<String> predicate = string -> string.contains(contained);
        addCheck("isContain", predicate);
        return this;
    }
}
