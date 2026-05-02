package com.mszlu.blog.handler;

import com.mszlu.blog.common.api.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 * @author wx
 */
// 对加 @controller 注解的方法进行拦截处理 aop 的实现
@ControllerAdvice
public class AllExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public CommonResult doException(Exception e){
    e.printStackTrace();
    return  CommonResult.fail(-999,"系统异常");
  }



}
