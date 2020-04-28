package org.qm.common.controller;

import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.common.utils.IdWorker;
import org.qm.common.validation.announce.CheckAssignPermArg;
import org.qm.common.validation.announce.CheckRoleArg;
import org.qm.domain.system.Role;
import org.qm.common.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value = "/sys/role")
public class RoleController {
    private RoleService roleService;
    private IdWorker idWorker;

    @Autowired
    public RoleController(RoleService roleService, IdWorker idWorker) {
        this.roleService = roleService;
        this.idWorker = idWorker;
    }

    //查询所有角色
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(ResultCode.SUCCESS, roleService.findAll());
    }

    //查询指定id的角色
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(ResultCode.SUCCESS, roleService.findById(id));
    }

    //添加角色
    @RequestMapping(method = RequestMethod.POST)
    public Result addRole(@RequestBody @CheckRoleArg Role role) {
        role.setId(idWorker.nextId() + "");
        roleService.save(role);
        return new Result(ResultCode.SUCCESS, role);
    }

    //删除角色
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteRole(@PathVariable String id) {
        return new Result(ResultCode.SUCCESS, roleService.delete(id));
    }


    //分配权限
    @RequestMapping(value = "/assignPerms", method = RequestMethod.POST)
    public Result assignPerms(@RequestBody @CheckAssignPermArg Map<String, Object> map) {
        roleService.assignPerms(map);
        return new Result(ResultCode.SUCCESS, map);
    }
}
