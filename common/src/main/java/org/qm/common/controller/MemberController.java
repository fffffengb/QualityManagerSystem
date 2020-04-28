package org.qm.common.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.qm.common.entity.PageResult;
import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.domain.base.Member;
import org.qm.common.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

//1.解决跨域
@CrossOrigin
//2.声明restController
@RestController
//3.使用验证器
@Validated
//3.设置父路径
@RequestMapping(value="/sys")

public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    /**
     * 保存
     */
    @RequiresPermissions(value = {"CRUDMember"})
    @RequestMapping(value = "/member", method = RequestMethod.POST)
    public Result save(@RequestBody Member member) {
        Member curMember = memberService.save(member);
        return new Result(ResultCode.SUCCESS, curMember);
    }

    /**
     * 模糊查询
     */
    @RequiresPermissions(value = {"CRUDMember"})
    @RequestMapping(value = "/member", method = RequestMethod.GET)
    public Result findAll(@NotNull Integer page, @NotNull Integer size, @RequestParam Map<String, Object> map) {
        Page<Member> pageUser = memberService.findAll(map, page, size);

        //3.构造返回结果
        PageResult<Member> pageResult = new PageResult<>(pageUser.getTotalElements(),pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 根据ID查询user
     */
    @RequiresPermissions(value = {"CRUDMember"})
    @RequestMapping(value = "/member/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") int id) {
        Member member = memberService.findById(id);
        return new Result(ResultCode.SUCCESS, member);
    }

    /**
     * 修改User
     */
    @RequiresPermissions(value = {"CRUDMember"})
    @RequestMapping(value = "/member/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") int id, @RequestBody Member member) {
        //1.设置修改的部门id
        member.setId(id);
        //2.调用service更新
        memberService.update(member);
        return new Result(ResultCode.SUCCESS, member);
    }

    /**
     * 根据id删除
     */
    @RequiresPermissions(value = {"CRUDMember"})
    @RequestMapping(value = "/member/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") int id) {
        memberService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
