package org.qm.common.controller;

import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ErrorController {

    //公共错误跳转
    @RequestMapping(value="authError")
    public Result authError(int code) {
        return code ==1?new Result(ResultCode.UNAUTHENTICATED):new Result(ResultCode.UNAUTHORISE);
    }

}
