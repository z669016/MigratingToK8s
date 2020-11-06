package liveproject.m2k8s.service;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class NewProfileTest {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        final NewProfile newProfile = new NewProfile("USERNAME", "PASSWORD", "FIRST", "LAST", "uid@firstlast.com");
        final Set<ConstraintViolation<UpdateProfile>> violations = validator.validate(newProfile);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenAllFieldsNullThenValidationSucceeds() {
        final NewProfile newProfile = new NewProfile();
        final Set<ConstraintViolation<UpdateProfile>> violations = validator.validate(newProfile);
        assertThat(violations.size()).isEqualTo(5);
    }

    @Test
    void whenAllFieldsEmptyThenValidationSucceeds() {
        final NewProfile newProfile = new NewProfile("", "", "", "", "");
        final Set<ConstraintViolation<UpdateProfile>> violations = validator.validate(newProfile);
        assertThat(violations.size()).isEqualTo(5);
    }
}