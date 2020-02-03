package org.qm.common.validation.announce;

import org.qm.common.validation.validate.CheckAssignPermArgImpl;
import org.qm.common.validation.validate.CheckAssignRoleArgImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CheckAssignPermArgImpl.class)
public @interface CheckAssignPermArg {
    //constraintValidatorContext.disableDefaultConstraintViolation();禁用了此Message
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
