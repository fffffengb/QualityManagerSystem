package org.qm.common.validation.validate;

import org.qm.common.validation.announce.CheckAssignPermArg;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class CheckAssignPermArgImpl implements ConstraintValidator<CheckAssignPermArg, Map<String, Object>> {
   public void initialize(CheckAssignPermArg constraint) {
   }

   public boolean isValid(Map<String, Object> map, ConstraintValidatorContext context) {
      // id : 指定要被分配的角色id; permIds : 要分配的权限集合
      StringBuilder errMessage = new StringBuilder("参数名指定错误：");
      Object id = map.get("id");
      Object permIds = map.get("permIds");
      if (id == null) errMessage.append("应该是id");
      if (permIds == null) errMessage.append("应该是permIds");
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(errMessage.toString()).addConstraintViolation();
      return id != null && permIds != null;
   }
}
