package com.mszlu.blog.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HttpServletRequest
 *
 * @author wx
 */
public class HttpContextUtils {

  public static HttpServletRequest getHttpServletRequest(){
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }


}