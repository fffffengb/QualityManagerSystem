package org.qm.common.exception;

import lombok.Getter;
import org.qm.common.entity.ResultCode;

/**
 * 自定义异常
 */
@Getter
public class CommonException extends Exception  {

    private ResultCode resultCode;

    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
