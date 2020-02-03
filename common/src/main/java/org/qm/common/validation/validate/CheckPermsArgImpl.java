package org.qm.common.validation.validate;

import org.qm.common.validation.announce.CheckPermsArg;
import org.qm.domain.system.Permission;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPermsArgImpl implements ConstraintValidator<CheckPermsArg, Permission> {
   public void initialize(CheckPermsArg constraint) {
   }

   public boolean isValid(Permission permission, ConstraintValidatorContext context) {
      return !(permission.getName() == null) && !(permission.getName().isEmpty());
   }
}
