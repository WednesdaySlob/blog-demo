package com.mszlu.blog.mbg.domain.vo.params;

import lombok.Data;

/**
 * 注册 请求参数
 * @author wx
 */
@Data
public class RegisterParam {

  private String account;

  private String password;

  private  String nickname;

}
