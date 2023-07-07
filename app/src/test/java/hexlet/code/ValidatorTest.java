package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
