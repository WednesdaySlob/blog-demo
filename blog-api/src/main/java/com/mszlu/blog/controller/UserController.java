package com.mszlu.blog.controller;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


  private final UserService userService;


  @ApiOperation("获取用户信息")
  @GetMapping("currentUser")
  public CommonResult currentUser(@RequestHeader("Authorization") String token){

    return userService.getUserInfoByToken(token);

  }

}
