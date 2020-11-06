package liveproject.m2k8s.service;

import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateProfileTest {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        final UpdateProfile updateProfile = new UpdateProfile("USERNAME", "FIRST", "LAST", "uid@firstlast.com");
        final Set<ConstraintViolation<UpdateProfile>> violations = validator.validate(updateProfile);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenAllFieldsNullThenValidationSucceeds() {
        final UpdateProfile updateProfile = new UpdateProfile();
        final Set<ConstraintViolation<UpdateProfile>> violations = validator.validate(updateProfile);
        assertThat(violations.size()).isEqualTo(4);
    }

    @Test
    void whenAllFieldsEmptyThenValidationSucceeds() {
        final UpdateProfile updateProfile = new UpdateProfile("", "", "", "");
        final Set<ConstraintViolation<UpdateProfile>> violations = validator.validate(updateProfile);
        assertThat(violations.size()).isEqualTo(4);
    }
}