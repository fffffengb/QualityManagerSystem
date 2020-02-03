package org.qm.common.validation.validate;

import org.qm.common.validation.announce.CheckAssignRoleArg;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class CheckAssignRoleArgImpl implements ConstraintValidator<CheckAssignRoleArg, Map<String, Object>> {

    @Override
    public boolean isValid(Map<String, Object> map, ConstraintValidatorContext context) {
         // id : 指定要被分配的角色id; permIds : 要分配的权限集合
        StringBuilder errMessage = new StringBuilder("参数名指定错误：");
        Object id = map.get("id");
        Object roleIds = map.get("roleIds");
        if (id == null) errMessage.append("应该是id");
        if (roleIds == null) errMessage.append("应该是permIds");
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errMessage.toString()).addConstraintViolation();
        return id != null && roleIds != null;
    }
}
