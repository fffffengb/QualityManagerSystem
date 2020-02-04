package org.qm.common.validation.validate;

import org.qm.common.validation.announce.CheckConfUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class CheckConfUserImpl implements ConstraintValidator<CheckConfUser, Map<String, String>> {
   public void initialize(CheckConfUser constraint) {
   }

   public boolean isValid(Map<String, String> map , ConstraintValidatorContext context) {
      //参数名和参数值都有限制
      boolean isValid = true;
      StringBuilder stringBuilder = new StringBuilder("参数验证错误：");
      //1.先检查参数名
      String confId = map.get("confId");
      String value = map.get("value");
      int val = 0;
      if (confId == null) {
         stringBuilder.append("应该是confId");
         isValid = false;
      }
      if (value == null) {
         stringBuilder.append("应该是value");
         isValid = false;
      }
//      else {
//         try {
//            val = Integer.parseInt(value);
//         } catch (NumberFormatException e){
//            stringBuilder.append("必须输入一个数字");
//            isValid = false;
//         }
//         if (val < 5 || val >60 ) {
//            stringBuilder.append("不能小于5或大于60");
//            isValid = false;
//         }
//      }
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(stringBuilder.toString()).addConstraintViolation();
      return isValid;
   }
}
