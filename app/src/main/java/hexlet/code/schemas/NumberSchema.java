package hexlet.code.schemas;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NumberSchema extends BaseSchema {
    private Boolean isRequired = Boolean.FALSE;
    private Boolean isPositive = Boolean.FALSE;
    private Integer rangeStart;
    private Integer rangeEnd;

    public final NumberSchema required() {
        this.isRequired = Boolean.TRUE;
        return this;
    }

    public final NumberSchema positive() {
        this.isPositive = Boolean.TRUE;
        return this;
    }


    public final NumberSchema range(int start, int end) {
        this.rangeStart = start;
        this.rangeEnd = end;
        return this;
    }

    @Override
    public final boolean isValid(Object object) {
        if (object == null) {
            return !isRequired;
        }

        if (!(object instanceof Number)) {
            return false;
        }

        int number = ((Number) object).intValue();

        if (this.isPositive && number <= 0) {
            return false;
        }

        if (rangeStart != null && rangeEnd != null) {
            return !checkRange(number);
        }

        return true;
    }

    private boolean checkRange(int number) {
        return number < Math.min(rangeStart, rangeEnd) || number > Math.max(rangeStart, rangeEnd);
    }
}
