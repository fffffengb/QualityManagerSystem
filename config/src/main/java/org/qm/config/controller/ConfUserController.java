package org.qm.config.controller;

import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.common.validation.announce.CheckConfUser;
import org.qm.common.validation.announce.CheckRoleArg;
import org.qm.config.service.ConfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/cfg/user")
@Validated
public class ConfUserController {

    private ConfUserService confUserService;

    @Autowired
    public ConfUserController(ConfUserService confUserService) {
        this.confUserService = confUserService;
    }

    //根据某条设置的ID查找当前用户设置
    @RequestMapping(value = "/{confId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String confId) {
        return new Result(ResultCode.SUCCESS, confUserService.findById(confId));
    }

    //修改当前用户某条设置(只能修改value)
    @RequestMapping(method = RequestMethod.PATCH)
    public Result updateValueById(@RequestBody @CheckConfUser Map<String, String> map) {
        return new Result(ResultCode.SUCCESS, confUserService.updateById(map));
    }
}
