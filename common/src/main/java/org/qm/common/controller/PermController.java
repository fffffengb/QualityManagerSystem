package org.qm.common.controller;

import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.common.utils.IdWorker;
import org.qm.common.validation.announce.CheckPermsArg;
import org.qm.domain.system.Permission;
import org.qm.common.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value = "/sys/perm")
public class PermController {
    private PermService permService;
    private IdWorker idWorker;

    @Autowired
    public PermController(PermService permService, IdWorker idWorker) {
        this.permService = permService;
        this.idWorker = idWorker;
    }

    //查询所有权限
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(ResultCode.SUCCESS, permService.findAll());
    }

    //根据id查询
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(ResultCode.SUCCESS, permService.findById(id));
    }

   //添加一个新的权限
    @RequestMapping(method = RequestMethod.POST)
    public Result addPerm(@RequestBody @CheckPermsArg Permission permission) {
        permission.setId(idWorker.nextId() + "");
        permService.save(permission);
        return new Result(ResultCode.SUCCESS, permission);
    }

    //修改一个已存在的权限
    @RequestMapping(method = RequestMethod.PATCH)
    public Result updatePerm(@RequestBody @CheckPermsArg Permission permission) {
        Permission newPermission = permService.update(permission);
        if (newPermission == null) return new Result(ResultCode.FAIL, "未找到此权限");
        return new Result(ResultCode.SUCCESS, newPermission);
    }

    //删除一个权限，如提供的id不存在则向前端返回提示信息
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deletePerm(@PathVariable String id) {
        Permission permission = permService.delete(id);
        return new Result(ResultCode.SUCCESS, permission);
    }
}
