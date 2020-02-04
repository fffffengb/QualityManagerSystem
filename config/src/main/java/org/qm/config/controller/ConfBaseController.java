package org.qm.config.controller;

import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.common.validation.announce.CheckConfBase;
import org.qm.config.service.ConfBaseService;
import org.qm.domain.conf.ConfBaseInquire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/cfg/base")
@Validated
public class ConfBaseController {
    private ConfBaseService confBaseService;

    @Autowired
    public ConfBaseController(ConfBaseService confBaseService) {
        this.confBaseService = confBaseService;
    }

    //添加一个基本设置
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody @CheckConfBase ConfBaseInquire confBase) {
        return new Result(ResultCode.SUCCESS, confBaseService.add(confBase));
    }

    //删除一个基本设置
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        return new Result(ResultCode.SUCCESS, confBaseService.deleteById(id));
    }

    //修改一个基本设置
    @RequestMapping(method = RequestMethod.PATCH)
    public Result update(@RequestBody ConfBaseInquire confBase) {
        return new Result(ResultCode.SUCCESS, confBaseService.update(confBase));
    }

    //查找所有基本设置
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(ResultCode.SUCCESS, confBaseService.findAll());
    }

    //根据ID查找一共设置
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(ResultCode.SUCCESS, confBaseService.findById(id));
    }

}
