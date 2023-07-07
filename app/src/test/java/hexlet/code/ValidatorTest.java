package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {
    Validator validator;

    @BeforeEach
    void beforeEach() {
        this.validator = new Validator();
    }

    @Test
    void testValidator() {
        StringSchema stringSchema = validator.string();
        assertTrue(stringSchema.isValid(null));
        assertTrue(stringSchema.isValid(""));
        assertFalse(stringSchema.isValid(5));

        stringSchema.required();
        assertFalse(stringSchema.isValid(null));
        assertFalse(stringSchema.isValid(""));
        assertFalse(stringSchema.isValid(5));

        stringSchema.minLength(5);
        assertFalse(stringSchema.isValid("abcd"));

        assertTrue(stringSchema.contains("a").isValid("ab cde"));
        assertTrue(stringSchema.contains("ab").isValid("ab cde"));
        assertFalse(stringSchema.contains("abcd").isValid("ab cde"));
        assertFalse(stringSchema.isValid("ab cde"));

        assertThrows(RuntimeException.class, () -> stringSchema.minLength(-1));
    }

    @Test
    void number() {
        NumberSchema numberSchema = validator.number();
        assertTrue(numberSchema.isValid(null));
        assertTrue(numberSchema.positive().isValid(null));

        numberSchema.required();
        assertFalse(numberSchema.isValid(null));
        assertFalse(numberSchema.isValid("5"));
        assertTrue(numberSchema.isValid(10));

        assertFalse(numberSchema.isValid(-10));
        assertFalse(numberSchema.isValid(0));

        numberSchema.range(5, 10);
        assertTrue(numberSchema.isValid(5));
        assertTrue(numberSchema.isValid(10));
        assertFalse(numberSchema.isValid(4));
        assertFalse(numberSchema.isValid(11));

        numberSchema.range(10, 5);
        assertTrue(numberSchema.isValid(5));
        assertTrue(numberSchema.isValid(10));
        assertFalse(numberSchema.isValid(4));
        assertFalse(numberSchema.isValid(11));
    }

    @Test
    void map() {
        MapSchema mapSchema = validator.map();

        assertTrue(mapSchema.isValid(null));

        mapSchema.required();

        assertFalse(mapSchema.isValid(null));
        assertFalse(mapSchema.isValid(""));
        assertTrue(mapSchema.isValid(new HashMap<>()));
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertTrue(mapSchema.isValid(data));

        mapSchema.sizeof(2);

        assertFalse(mapSchema.isValid(data));
        data.put("key2", "value2");
        assertTrue(mapSchema.isValid(data));
    }

    @Test
    void mapWithShape() {
        Map<String, BaseSchema> schemas = new HashMap<>();
        MapSchema mapSchema = validator.map();

        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number().positive().range(18, 100));

        mapSchema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        assertTrue(mapSchema.isValid(human1));

        Map<String, Object> otherMap = new HashMap<>();
        otherMap.put("key", "value");
        otherMap.put("key2", "value2");
        assertFalse(mapSchema.isValid(otherMap));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertTrue(mapSchema.isValid(human2));

        human2.put("weight", 50);
        assertTrue(mapSchema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertFalse(mapSchema.isValid(human3));

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -5);
        assertFalse(mapSchema.isValid(human4));

        Map<String, Object> humanoid = new HashMap<>();
        human4.put("name", 123);
        human4.put("age", 5);
        assertFalse(mapSchema.isValid(humanoid));

        mapSchema.sizeof(3);
        assertFalse(mapSchema.isValid(human1));
    }
}
