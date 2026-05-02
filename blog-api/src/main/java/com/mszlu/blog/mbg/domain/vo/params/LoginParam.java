package com.mszlu.blog.mbg.domain.vo.params;

import lombok.Data;

/**
 * 登录 请求参数
 * @author wx
 */
@Data
public class LoginParam {

  private String account;

  private String password;

}
