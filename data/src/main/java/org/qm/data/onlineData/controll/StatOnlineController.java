package org.qm.data.onlineData.controll;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.qm.common.entity.PageResult;
import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.domain.data.stat.DStatOnline;
import org.qm.data.onlineData.service.StatOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//解决跨域
@CrossOrigin
//声明restController
@RestController
//设置父路径
@RequestMapping(value="/online")
//参数校验
@Validated
public class StatOnlineController {

    private StatOnlineService statOnlineService;

    @Autowired
    public StatOnlineController(StatOnlineService statOnlineService) {
        this.statOnlineService = statOnlineService;
    }

    //直接查询自己管辖下的所有时间的工位数据。
    @RequestMapping(value = "/stat/{page}/{size}", method = RequestMethod.GET)
    public Result findAll( @PathVariable @Min(value = 1) int page, @PathVariable @Min(value = 1) int size) {
        Page<DStatOnline> allManaged = statOnlineService.findAllByStatIdAndTime(null, null, null, "quality", page, size);
        PageResult<DStatOnline> pageResult =
                new PageResult<>(allManaged.getTotalElements(), allManaged.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    //查询指定工位的数据，如果超出自己的管辖范围，提示权限不足。
    @RequestMapping(value = "/stat/{statId}/{page}/{size}", method = RequestMethod.GET)
    public Result findByStatId(@PathVariable String statId, @PathVariable @Min(value = 1) int page, @PathVariable @Min(value = 1) int size) {
        Page<DStatOnline> allOnlineData = statOnlineService.findAllByStatIdAndTime(statId, null, null, "quality", page, size);
        PageResult<DStatOnline> pageResult =
                new PageResult<>(allOnlineData.getTotalElements(), allOnlineData.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    // 查询给定时间中，自己管理的所有工位数据
    @RequestMapping(value = "/stat/{statId}/{start}/{end}/{page}/{size}", method = RequestMethod.GET)
    public Result findByStatId(@PathVariable String statId,
                               @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd_HH:mm:ss") Date start,
                               @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd_HH:mm:ss") Date end,
                               @PathVariable @Min(value = 1) int page, @PathVariable @Min(value = 1) int size) {
        Page<DStatOnline> allOnlineData = statOnlineService.findAllByStatIdAndTime(statId, start, end, "quality", page, size);
        PageResult<DStatOnline> pageResult =
                new PageResult<>(allOnlineData.getTotalElements(), allOnlineData.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

}
