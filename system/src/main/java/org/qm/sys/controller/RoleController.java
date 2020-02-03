package org.qm.sys.controller;

import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.common.utils.IdWorker;
import org.qm.common.validation.announce.CheckAssignPermArg;
import org.qm.common.validation.announce.CheckRoleArg;
import org.qm.domain.system.Role;
import org.qm.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value = "/rp")
public class RoleController {
    private RoleService roleService;
    private IdWorker idWorker;

    @Autowired
    public RoleController(RoleService roleService, IdWorker idWorker) {
        this.roleService = roleService;
        this.idWorker = idWorker;
    }

    //查询所有角色
    @RequestMapping(value = "role", method = RequestMethod.GET)
    public Result findAll() {
        List<String> roles = roleService.findAll();
        return new Result(ResultCode.SUCCESS, roles);
    }

    //添加角色
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public Result addRole(@RequestBody @CheckRoleArg Role role) {
        role.setId(idWorker.nextId() + "");
        roleService.save(role);
        return new Result(ResultCode.SUCCESS, role);
    }

    //分配权限
    @RequestMapping(value = "assignPerms", method = RequestMethod.POST)
    public Result assignPerms(@RequestBody @CheckAssignPermArg Map<String, Object> map) {
        roleService.assignPerms(map);
        return new Result(ResultCode.SUCCESS, map);
    }
}
