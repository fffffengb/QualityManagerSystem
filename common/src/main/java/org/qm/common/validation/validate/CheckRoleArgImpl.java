package org.qm.common.validation.validate;

import org.qm.common.validation.announce.CheckRoleArg;
import org.qm.domain.system.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckRoleArgImpl implements ConstraintValidator<CheckRoleArg, Role> {
   public void initialize(CheckRoleArg constraint) {
   }

   public boolean isValid(Role role, ConstraintValidatorContext context) {
      return !(role.getName() == null) && !(role.getName().isEmpty());
   }
}
