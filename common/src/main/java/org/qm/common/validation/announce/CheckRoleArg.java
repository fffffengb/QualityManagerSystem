package org.qm.common.validation.announce;

import org.qm.common.validation.validate.CheckRoleArgImpl;

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
@Constraint(validatedBy = CheckRoleArgImpl.class)
public @interface CheckRoleArg {
    String message() default "name字段不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
