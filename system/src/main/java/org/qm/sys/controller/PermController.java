package org.qm.sys.controller;

import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.common.utils.IdWorker;
import org.qm.common.validation.announce.CheckPermsArg;
import org.qm.domain.system.Permission;
import org.qm.sys.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value = "/rp")
public class PermController {
    private PermService permService;
    private IdWorker idWorker;

    @Autowired
    public PermController(PermService permService, IdWorker idWorker) {
        this.permService = permService;
        this.idWorker = idWorker;
    }

    @RequestMapping(value = "/perm", method = RequestMethod.POST)
    public Result addPerm(@RequestBody @CheckPermsArg Permission permission) {
        permission.setId(idWorker.nextId() + "");
        permService.save(permission);
        return new Result(ResultCode.SUCCESS, permission);
    }

    @RequestMapping(value = "/perm", method = RequestMethod.PATCH)
    public Result updatePerm(@RequestBody @CheckPermsArg Permission permission) {
        Permission newPermission = permService.update(permission);
        if (newPermission == null) return new Result(ResultCode.FAIL, "未找到此权限");
        return new Result(ResultCode.SUCCESS, newPermission);
    }

}
