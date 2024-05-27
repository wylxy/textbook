package com.sheepion.aop;

import com.sheepion.common.Result;
import com.sheepion.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public Result<String> handleException(Exception ex) {
        log.debug("报错信息：");
        log.warn(ex.toString());
        ex.printStackTrace();
        // 创建一个包含错误信息的对象
        return Result.failed(ResultCode.FAILED, "服务器发生内部错误\n" +
                ex.toString());
    }

    @ResponseBody
    @ExceptionHandler(value = {SQLException.class})
    public Result<String> handleSqlException(Exception ex) {
        log.debug("报错信息：");
        log.warn(ex.toString());
        // 创建一个包含错误信息的对象
        return Result.failed(ResultCode.FAILED, "数据库发生错误");
    }

    @ResponseBody
    @ExceptionHandler(value = {
            DataIntegrityViolationException.class,
            SQLIntegrityConstraintViolationException.class})
    public Result<String> handleSqlIntegrityConstraintViolationException(Exception ex) {
        log.debug("报错信息：");
        log.warn(ex.toString());
        // 创建一个包含错误信息的对象
        return Result.failed(ResultCode.FAILED, "数据库发生错误，完整性约束违规");
    }

    @ResponseBody
    @ExceptionHandler({
            MissingServletRequestParameterException.class
    })
    public Result<String> handleMissingServletRequestParameterException(Exception ex) {
        log.debug("报错信息：");
        log.warn(ex.toString());
        // 创建一个包含错误信息的对象
        return Result.failed(ResultCode.VALIDATE_FAILED, "缺少请求参数");
    }
}
