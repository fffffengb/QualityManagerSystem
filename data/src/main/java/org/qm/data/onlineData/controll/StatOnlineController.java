package org.qm.data.onlineData.controll;

import org.qm.common.entity.PageResult;
import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.domain.data.stat.DStatOnline;
import org.qm.data.onlineData.service.StatOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

//1.解决跨域
@CrossOrigin
//2.声明restController
@RestController
//3.设置父路径
@RequestMapping(value="/online")
//4.参数校验
@Validated
public class StatOnlineController {

    private StatOnlineService statOnlineService;

    @Autowired
    public StatOnlineController(StatOnlineService statOnlineService) {
        this.statOnlineService = statOnlineService;
    }

    //直接查询自己管辖下的所有工位数据。
    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public Result findAll(@Min(value = 1) int page, @Min(value = 1) int size) {
        Page<DStatOnline> allManaged = statOnlineService.findAllManaged(page, size);
        PageResult<DStatOnline> pageResult =
                new PageResult<>(allManaged.getTotalElements(), allManaged.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    //查询指定工位的数据，如果超出自己的管辖范围，提示权限不足。
    @RequestMapping(value = "/stat/{statId}", method = RequestMethod.GET)
    public Result findByStatId(@PathVariable String statId, @Min(value = 1) int page, @Min(value = 1) int size) {
        if (!statOnlineService.hasPermFind(statId)) return new Result(ResultCode.UNAUTHORISE);
        Page<DStatOnline> allOnlineData = statOnlineService.findAllByStatId(statId, page, size);
        PageResult<DStatOnline> pageResult =
                new PageResult<>(allOnlineData.getTotalElements(), allOnlineData.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }
}
