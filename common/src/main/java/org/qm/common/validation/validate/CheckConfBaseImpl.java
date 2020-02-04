package org.qm.common.validation.validate;

import org.qm.common.validation.announce.CheckConfBase;
import org.qm.domain.conf.ConfBaseInquire;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckConfBaseImpl implements ConstraintValidator<CheckConfBase, ConfBaseInquire> {
   public void initialize(CheckConfBase constraint) {
   }

   public boolean isValid(ConfBaseInquire confBase, ConstraintValidatorContext context) {
      String name = confBase.getName();
      String description = confBase.getDescription();
      StringBuilder errMessage = new StringBuilder("参数名指定错误：");
      if (name == null) errMessage.append("参数name不能为空");
      if (description == null) errMessage.append("参数description不能为空");
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(errMessage.toString()).addConstraintViolation();
      return name != null && description != null;
   }
}
