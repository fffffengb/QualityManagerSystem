package org.qm.config.service;

import org.qm.common.dao.configDao.ConfUserDao;
import org.qm.common.exception.ArgValidateException;
import org.qm.common.utils.QueryUtils;
import org.qm.domain.conf.ConfBaseInquire;
import org.qm.domain.conf.ConfUserInquire;
import org.qm.domain.system.response.ProfileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConfUserService {
    private ConfUserDao confUserDao;
    private ConfBaseService confBaseService;
    private QueryUtils queryUtils;
    @Autowired
    public ConfUserService(ConfUserDao confUserDao, ConfBaseService confBaseService, QueryUtils queryUtils) {
        this.confUserDao = confUserDao;
        this.confBaseService = confBaseService;
        this.queryUtils = queryUtils;
    }

    //查找当前用户某条设置
    public ConfUserInquire findById(String confId) {
        //如果没有这条confId,则给前端返回提示信息
        validateConfId(confId);
        return confUserDao.findByConfIdAndEmployeeId(confId, getCurUserId());
    }

    //修改当前用户某条设置
    public ConfUserInquire updateById(Map<String, String> map) {
        String confId = map.get("confId");
        String value = map.get("value");
        //先查找有没有这个设置
        ConfUserInquire target = findById(confId);
        //再验证Value的合法性
        validateValue(confId, value);
        //保存设置
        target.setValue(map.get("value"));
        confUserDao.save(target);
        return target;
    }


    private String getCurUserId() {
        ProfileResult curUser = queryUtils.getCurUser();
        return curUser.getId();
    }

    private void validateConfId(String confId) {
        confBaseService.findById(confId);
    }

    private void validateValue(String confId, String value) {
        ConfBaseInquire cfg = confBaseService.findById(confId);
        //是查询默认时间间隔则范围应该是(5-60)
        if (cfg.getName().equals("defaultQueryInterval")) {
            int val;
            try {
                val = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new ArgValidateException("必须是数字");
            }
            if (val < 5 || val > 60) throw new ArgValidateException("值不能小于5或大于60");
        }else if (!value.equals("quality") && !value.equals("workHour")) {
            //是排名优先级则只能是quality/workHour
            throw new ArgValidateException("必须是quality或者workHour");
        }
    }
}
