package org.qm.data.avgData.controller;

import org.qm.data.avgData.service.StatAvgService;
import org.qm.common.entity.PageResult;
import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.domain.data.stat.DStatAvg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@CrossOrigin
@RestController
@RequestMapping("/avg")
@Validated
public class StatAvgController {
    private StatAvgService statAvgService;

    @Autowired
    public StatAvgController(StatAvgService statAvgService) {
        this.statAvgService = statAvgService;
    }

    //直接查询自己管辖下的所有工位数据。
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public Result findAll(@Min(value = 1) int page, @Min(value = 1) int size) {
        Page<DStatAvg> allManaged = statAvgService.findAllManaged(page, size);
        PageResult<DStatAvg> pageResult =
                new PageResult<>(allManaged.getTotalElements(), allManaged.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    //查询指定工位的数据，如果超出自己的管辖范围，提示权限不足。
    @RequestMapping(value = "/stat/{statId}", method = RequestMethod.GET)
    public Result findByStatId(@PathVariable String statId, @Min(value = 1) int page, @Min(value = 1) int size) {
        if (!statAvgService.hasPermFind(statId)) return new Result(ResultCode.UNAUTHORISE);
        Page<DStatAvg> allAvgData = statAvgService.findAllByStatId(statId, page, size);
        PageResult<DStatAvg> pageResult =
                new PageResult<>(allAvgData.getTotalElements(), allAvgData.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }
}
