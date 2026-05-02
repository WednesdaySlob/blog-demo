package com.mszlu.blog.controller;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.vo.params.RegisterParam;
import com.mszlu.blog.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册
 *
 * @author wx
 */
@RestController
@RequestMapping("register")
@RequiredArgsConstructor
public class RegisterController {

  private final RegisterService registerService;


  @PostMapping
  public CommonResult register(@RequestBody RegisterParam registerParam) {
    return registerService.register(registerParam);
  }

}
