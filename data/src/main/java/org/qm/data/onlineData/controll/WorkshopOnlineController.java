package org.qm.data.onlineData.controll;

import org.qm.common.entity.PageResult;
import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.data.onlineData.service.WorkshopOnlineService;
import org.qm.domain.data.stat.DStatOnline;
import org.qm.domain.data.workshop.DWorkshopOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Date;

//声明restController
@RestController
//设置父路径
@RequestMapping(value="/online")
//参数校验
@Validated
public class WorkshopOnlineController {

    WorkshopOnlineService workshopOnlineService;
    @Autowired
    WorkshopOnlineController(WorkshopOnlineService workshopOnlineService) {
        this.workshopOnlineService = workshopOnlineService;
    }

    @RequestMapping(value = "/workshop/{page}/{size}", method = RequestMethod.GET)
    public Result findAll(@PathVariable @Min(value = 1) int page, @PathVariable @Min(value = 1) int size) {
        Page<DWorkshopOnline> allManaged = workshopOnlineService.findAllByWorkshopIdAndTime(null, null, null, "quality", page, size);
        PageResult<DWorkshopOnline> pageResult =
                new PageResult<>(allManaged.getTotalElements(), allManaged.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    //查询指定工位的数据，如果超出自己的管辖范围，提示权限不足。
    @RequestMapping(value = "/workshop/{workshopId}/{page}/{size}", method = RequestMethod.GET)
    public Result findByStatId(@PathVariable String workshopId, @PathVariable @Min(value = 1) int page, @PathVariable @Min(value = 1) int size) {
        Page<DWorkshopOnline> allOnlineData = workshopOnlineService.findAllByWorkshopIdAndTime(workshopId, null, null, "quality", page, size);
        PageResult<DWorkshopOnline> pageResult =
                new PageResult<>(allOnlineData.getTotalElements(), allOnlineData.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    // 查询给定时间中，自己管理的所有工位数据
    @RequestMapping(value = "/workshop/{workshopId}/{start}/{end}/{page}/{size}", method = RequestMethod.GET)
    public Result findByStatId(@PathVariable String workshopId,
                               @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd_HH:mm:ss") Date start,
                               @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd_HH:mm:ss") Date end,
                               @PathVariable @Min(value = 1) int page, @PathVariable @Min(value = 1) int size) {
        Page<DWorkshopOnline> allOnlineData = workshopOnlineService.findAllByWorkshopIdAndTime(workshopId, start, end, "quality", page, size);
        PageResult<DWorkshopOnline> pageResult =
                new PageResult<>(allOnlineData.getTotalElements(), allOnlineData.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }
}
