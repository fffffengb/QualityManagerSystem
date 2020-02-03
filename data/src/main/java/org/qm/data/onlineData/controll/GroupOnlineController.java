package org.qm.data.onlineData.controll;

import org.qm.common.entity.PageResult;
import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.domain.data.group.DGroupOnline;
import org.qm.data.onlineData.service.GroupOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//1.解决跨域
@CrossOrigin
//2.声明restController
@RestController
//3.设置父路径
@RequestMapping(value="/online")
//4.参数校验
@Validated
public class GroupOnlineController {
    private GroupOnlineService groupOnlineService;

    @Autowired
    public GroupOnlineController(GroupOnlineService groupOnlineService) {
        this.groupOnlineService = groupOnlineService;
    }

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public Result findAll(int page, int size) {
        Page<DGroupOnline> groupOnlinePage = groupOnlineService.findAll(page, size);
        return new Result(ResultCode.SUCCESS, new PageResult<>(groupOnlinePage.getTotalElements(), groupOnlinePage.getContent()));
    }

    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public Result findAllByGroupId(@PathVariable String groupId, int page, int size) {
        if (!groupOnlineService.hasPermFind(groupId)) return new Result(ResultCode.UNAUTHORISE);
        Page<DGroupOnline> groupOnlinePage = groupOnlineService.findAllByGroupId(groupId, page, size);
        return new Result(ResultCode.SUCCESS, new PageResult<>(groupOnlinePage.getTotalElements(), groupOnlinePage.getContent()));
    }
}
