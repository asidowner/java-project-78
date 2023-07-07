package hexlet.code.schemas;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StringSchema extends BaseSchema {
    private Boolean isRequired = Boolean.FALSE;
    private int minLengthValue = 0;
    private String containedText = "";

    public final StringSchema required() {
        this.isRequired = Boolean.TRUE;
        return this;
    }

    public final StringSchema minLength(int minLength) {
        if (minLength < 0) {
            throw new RuntimeException("The minimum length must be greater than zero");
        }
        this.minLengthValue = minLength;
        return this;
    }

    public final StringSchema contains(String contained) {
        this.containedText = contained;
        return this;
    }

    @Override
    public final boolean isValid(Object object) {
        if (object == null || object.toString().equals("")) {
            return !isRequired;
        }

        if (!(object instanceof String)) {
            return false;
        }

        String text = object.toString();

        return checkMinLength(text) && checkContains(text);
    }

    private boolean checkMinLength(String text) {
        return text.length() >= minLengthValue;
    }

    private boolean checkContains(String text) {
        return text.contains(containedText);
    }
}
