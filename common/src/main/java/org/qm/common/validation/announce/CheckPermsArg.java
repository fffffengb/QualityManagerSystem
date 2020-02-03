package org.qm.common.validation.announce;

import org.qm.common.validation.validate.CheckPermsArgImpl;

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
@Constraint(validatedBy = CheckPermsArgImpl.class)
public @interface CheckPermsArg {
    String message() default "name字段不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
