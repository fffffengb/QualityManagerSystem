package org.qm.common.controller;

import org.qm.common.entity.PageResult;
import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.common.entity.UserResult;
import org.qm.common.validation.announce.CheckAssignRoleArg;
import org.qm.domain.system.User;
import org.qm.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

//1.解决跨域
@CrossOrigin
//2.声明restController
@RestController
//3.声明验证器
@Validated
//4.设置父路径
@RequestMapping(value="/sys")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> map) {
        return new Result(ResultCode.SUCCESS, userService.login(map.get("username"), map.get("password")));
    }

    /**
     * 注册
     */
    @PostMapping(value = "/register")
    public Result register(@RequestBody Map<String, Object> map){
        return new Result(ResultCode.SUCCESS,
                userService.register(Integer.decode(map.get("employeeId").toString()), (String)map.get("username"), (List<String>) map.get("roleIds"), (String)map.get("password") ));
    }


    @RequestMapping(value = "/user/{page}/{size}", method = RequestMethod.GET)
    public Result findAll(@PathVariable @Min(value = 1) int page, @PathVariable @Min(value = 1) int size, @RequestParam Map<String, Object> map) {
        Page<UserResult> pageUser = userService.findAll(null, null, page, size);
        //3.构造返回结果
        PageResult<UserResult> pageResult = new PageResult<>(pageUser.getTotalElements(),pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    @RequestMapping(value = "/user/{userName}/{page}/{size}", method = RequestMethod.GET)
    public Result findByUsername(@PathVariable @Min(value = 1) int page, @PathVariable @Min(value = 1) int size, @PathVariable String userName) {
        Page<UserResult> pageUser = userService.findAll(null, userName, page, size);
        //3.构造返回结果
        PageResult<UserResult> pageResult = new PageResult<>(pageUser.getTotalElements(),pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    @RequestMapping(value = "/user/{userId}/{userName}/{page}/{size}", method = RequestMethod.GET)
    public Result findByUsername(@PathVariable @Min(value = 1) int page, @PathVariable @Min(value = 1) int size, @PathVariable String userName, @PathVariable String userId) {
        Page<UserResult> pageUser = userService.findAll(Integer.parseInt(userId), userName, page, size);
        //3.构造返回结果
        PageResult<UserResult> pageResult = new PageResult<>(pageUser.getTotalElements(),pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 修改User
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") int id, @RequestBody User user) {
        //1.设置修改的部门id
        user.setId(id);
        //2.调用service更新
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     */
    @Transactional
    @RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String username) {
        return new Result(ResultCode.SUCCESS, userService.deleteByUsername(username));
    }

    /**
     * 给用户分配角色
     */
    @RequestMapping(value = "/user/assignRole", method = RequestMethod.POST)
    public Result assignRole(@RequestBody @CheckAssignRoleArg Map<String, Object> map) {
        userService.assignRole((int)map.get("userId"), (List<String>) map.get("roleIds"));
        return new Result(ResultCode.SUCCESS, map);
    }
}
