package com.mszlu.blog.handler;

import com.alibaba.fastjson2.JSON;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.common.utils.JWTUtils;
import com.mszlu.blog.common.utils.UserThreadLocal;
import com.mszlu.blog.mbg.domain.entity.SysUser;
import com.mszlu.blog.mbg.domain.vo.ErrorCode;
import com.mszlu.blog.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * 登录拦截器
 * @author wx
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

  private final LoginService loginService;



  /**
   * 1. 需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
   * 2. 判断 token是否为空，如果为空 未登录
   * 3. 如果token 不为空，登录验证 loginService checkToken
   * 4. 如果认证成功 放行即可
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    //在执行controller方法(Handler)之前进行执行
    //handler 可能是 RequestResourceHandler springboot 程序 访问静态资源 默认去classpath下的static目录去查询
      if (!(handler instanceof HandlerMethod)){
        return true;
      }
    String token = request.getHeader("Authorization");
    log.info("=================request start===========================");
    String requestURI = request.getRequestURI();
    log.info("request uri:{}",requestURI);
    log.info("request method:{}",request.getMethod());
    log.info("token:{}", token);
    log.info("=================request end===========================");

    if (token == null){
      CommonResult result = CommonResult.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
      response.setContentType("application/json;charset=utf-8");
      response.getWriter().print(JSON.toJSONString(result));
      return false;
    }
    SysUser sysUser = loginService.checkToken(token);
    if (sysUser == null){
      CommonResult result = CommonResult.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
      response.setContentType("application/json;charset=utf-8");
      response.getWriter().print(JSON.toJSONString(result));
      return false;
    }
    //登录验证成功，放行
    //我希望在controller中 直接获取用户的信息 怎么获取?
    UserThreadLocal.put(sysUser);
    return true;
  }



  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    UserThreadLocal.remove();
  }


}
