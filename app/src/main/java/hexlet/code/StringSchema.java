package hexlet.code;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StringSchema {
    private Boolean required = Boolean.FALSE;
    private int minLengthValue = 0;
    private String containedText = "";

    public StringSchema required() {
        required = Boolean.TRUE;
        return this;
    }

    public StringSchema minLength(int minLength) {
        if (minLength < 0) {
            throw new RuntimeException("The minimum length must be greater than zero");
        }
        this.minLengthValue = minLength;
        return this;
    }

    public StringSchema contains(String contained) {
        this.containedText = contained;
        return this;
    }

    public boolean isValid(Object object) {
        if (object == null) {
            return !required;
        }

        if (object.toString().equals("")) {
            return !required;
        }

        if (!(object instanceof String)) {
            return false;
        }

        String text = object.toString();

        if (!checkMinLength(text)) {
            return false;
        }

        return checkContains(text);
    }

    private boolean checkMinLength(String text) {
        return text.length() >= minLengthValue;
    }

    private boolean checkContains(String text) {
        return text.contains(containedText);
    }
}
