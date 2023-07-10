package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public class NumberSchema extends BaseSchema<Number, NumberSchema> {

    public NumberSchema() {
        addCheck("isInstanceOf", Objects::nonNull);
    }

    public final NumberSchema required() {
        super.setIsRequired();
        return this;
    }


    public final NumberSchema positive() {
        addCheck("isPositive", number -> number.intValue() > 0);
        return this;
    }


    public final NumberSchema range(int start, int end) {
        int minValue = Math.min(start, end);
        int maxValue = Math.max(start, end);
        Predicate<Number> predicate = number -> number.intValue() >= minValue && number.intValue() <= maxValue;
        addCheck("isInRange", predicate);
        return this;
    }
}
