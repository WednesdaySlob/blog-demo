package com.mszlu.blog.controller;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.vo.params.LoginParam;
import com.mszlu.blog.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wx
 */
@RestController
@RequestMapping("logout")
@RequiredArgsConstructor
public class LogoutController {

  private final LoginService loginService;

  @PostMapping
  public CommonResult logout(@RequestHeader("Authorization") String token){
    return loginService.logout(token);
  }
  

}