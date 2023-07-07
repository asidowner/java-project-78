### Hexlet tests and linter status:

[![Actions Status](https://github.com/asidowner/java-project-78/workflows/hexlet-check/badge.svg)](https://github.com/asidowner/java-project-78/actions)

### CodeClimate

[![Maintainability](https://api.codeclimate.com/v1/badges/0c0ac6968e5990b156c3/maintainability)](https://codeclimate.com/github/asidowner/java-project-78/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/0c0ac6968e5990b156c3/test_coverage)](https://codeclimate.com/github/asidowner/java-project-78/test_coverage)

## Project check status:

[![check](https://github.com/asidowner/java-project-78/actions/workflows/check.yml/badge.svg)](https://github.com/asidowner/java-project-78/actions/workflows/check.yml)

## Requirements

* JDK-17

## Example

### String

```java
import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;

class App {
    public static void main(String[] args) {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();

        stringSchema.isValid(null); // true
        stringSchema.isValid(""); // true

        stringSchema.required();

        stringSchema.isValid(null); // false
        stringSchema.isValid(""); // false

        stringSchema.minLengthValue(3).isValid("abc"); // true
        stringSchema.isValid("ab"); // false bcz minLengthValue == 3;

    }
}
```

### Number

```java
import hexlet.code.Validator;
import hexlet.code.schemas.NumberSchema;

class App {
    public static void main(String[] args) {
        Validator validator = new Validator();
        NumberSchema numberSchema = validator.number();

        numberSchema.isValid(null); // true
        numberSchema.isValid(""); // false

        numberSchema.required();

        numberSchema.isValid(null); // false
        numberSchema.isValid(5); // true

        numberSchema.positive().isValid(4); // true
        numberSchema.isValid(-1); // false

        numberSchema.range(2, 5);
        numberSchema.isValid(2); // true
        numberSchema.isValid(1); // false
        numberSchema.isValid(5); // true
        numberSchema.isValid(6); // false
    }
}
```

### Map

```java
import java.util.HashMap;
import java.util.Map;

import hexlet.code.Validator;
import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;


class App {
    public static void main(String[] args) {
        Validator validator = new Validator();
        MapSchema mapSchema = validator.map();

        mapSchema.isValid(null); // true
        mapSchema.isValid(new HashMap<>()); // true

        mapSchema.required();

        mapSchema.isValid(null); // false
        mapSchema.isValid(new HashMap<>()); // true

        mapSchema.sizeof(1);
        mapSchema.isValid(new HashMap<>()); // false
        mapSchema.isValid(Map.of("key", "value")); // true

        Map<String, BaseSchema> schemas = Map.of(
                "key", validator.string().required(),
                "key2", validator.number().positive()
        );

        mapSchema.shape(schemas);
        
        mapSchema.isValid(Map.of("key", "a", "key2", 2)); // true
        mapSchema.isValid(Map.of("key", "a", "key2", null)); // true
        mapSchema.isValid(Map.of("key", 2, "key2", 2)); // false
        mapSchema.isValid(Map.of("key", "a", "key2", -1)); // false
    }
}
```