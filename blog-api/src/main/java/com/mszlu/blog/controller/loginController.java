package com.mszlu.blog.controller;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.vo.params.LoginParam;
import com.mszlu.blog.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录请求
 *
 * @author wx
 */
@RestController
@RequestMapping("login")
@RequiredArgsConstructor
public class loginController {

  private final LoginService loginService;

  @PostMapping
  public CommonResult login(@RequestBody LoginParam loginParam) {

    return loginService.login(loginParam);

  }

}

