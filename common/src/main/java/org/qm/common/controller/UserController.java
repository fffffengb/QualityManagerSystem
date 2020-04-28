package org.qm.common.controller;

import org.qm.common.entity.PageResult;
import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.common.validation.announce.CheckAssignRoleArg;
import org.qm.domain.system.User;
import org.qm.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 查询企业的部门列表
     * 指定企业id
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam Map<String, Object> map) {
        Page<User> pageUser = userService.findAll(map,page,size);
        //3.构造返回结果
        PageResult<User> pageResult = new PageResult<>(pageUser.getTotalElements(),pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }

    /**
     * 根据ID查询user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") int id) {
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS, user);
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
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") int id) {
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
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
