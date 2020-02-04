package org.qm.common.handler;


import org.qm.common.entity.Result;
import org.qm.common.entity.ResultCode;
import org.qm.common.exception.ArgValidateException;
import org.qm.common.exception.CommonException;
import org.qm.common.exception.NoSuchIdException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * 自定义的公共异常处理器
 *      1.声明异常处理器
 *      2.对异常统一处理
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    //处理参数验证异常
    @ExceptionHandler(value = {ConstraintViolationException.class, ArgValidateException.class})
    public Result validateException(HttpServletRequest request, HttpServletResponse response, Exception e) {

        return new Result(ResultCode.VALID_ERR, e.getMessage());
    }

    //处理参数不匹配异常
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Result argMismatchException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        return new Result(ResultCode.ARG_ERR, e.getMessage());
    }

    //处理认证异常
    @ExceptionHandler(value = org.apache.shiro.authz.UnauthorizedException.class)
    public Result unauthorizedException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        return new Result(ResultCode.UNAUTHORISE, e.getMessage());
    }

    //处理未找到Id异常
    @ExceptionHandler(value = NoSuchIdException.class)
    public Result noSuchIdException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        return new Result(ResultCode.FAIL, e.getMessage());
    }

    //处理其他异常
    @ExceptionHandler(value = Exception.class)
    public Result error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        if(e.getClass() == CommonException.class) {
            //类型转型
            CommonException ce = (CommonException) e;
            return new Result(ce.getResultCode());
        }else{
            return new Result(ResultCode.SERVER_ERROR);
        }
    }


}
