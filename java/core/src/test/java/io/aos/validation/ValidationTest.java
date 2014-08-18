package io.aos.validation;

import static org.junit.Assert.assertFalse;
import io.aos.model.m4.Restaurant;
import io.aos.validation.CreateGroup;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.bval.jsr303.DefaultMessageInterpolator;
import org.junit.Before;
import org.junit.Test;

public class ValidationTest {

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    static {
        ((DefaultMessageInterpolator) factory.getMessageInterpolator()).setLocale(Locale.ENGLISH);
    }

    protected Validator validator;

    @Before
    public void setUp() throws Exception {
        validator = createValidator();
    }

    protected Validator createValidator() {
        return factory.getValidator();
    }

    @Test
    public void testRestaurantModel() {
        Restaurant restaurant = new Restaurant();
        Set<ConstraintViolation<Restaurant>> violations = this.validator.validate(restaurant, CreateGroup.class);
        assertFalse(violations.isEmpty());
        for (ConstraintViolation<?> cv : violations) {
            System.out.println(cv.getMessage());
        }
    }

}
