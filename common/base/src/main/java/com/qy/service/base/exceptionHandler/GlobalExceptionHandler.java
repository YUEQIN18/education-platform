package com.qy.service.base.exceptionHandler;


import com.qy.service.base.exception.EduException;
import com.qy.service.utils.ExceptionUtils;
import com.qy.service.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qinyue
 * @create 2022-10-02 02:00:00
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error (Exception e){
        e.printStackTrace();
        return R.error().setMessage("全局异常处理");
    }

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error (EduException e){
        e.printStackTrace();
        log.error(ExceptionUtils.getMessage(e));
        return R.error().setCode(e.getCode()).setMessage(e.getMsg());
    }
}
