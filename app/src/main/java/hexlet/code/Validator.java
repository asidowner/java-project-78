package hexlet.code;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Validator {
    public StringSchema string() {
        return new StringSchema();
    }

    public NumberSchema number() {
        return new NumberSchema();
    }
}