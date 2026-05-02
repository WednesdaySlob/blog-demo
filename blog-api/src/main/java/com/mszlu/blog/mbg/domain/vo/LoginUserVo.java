package com.mszlu.blog.mbg.domain.vo;

import lombok.Data;

/**
 * 展示层对象，前端页面展示
 * @author wx
 */
@Data
public class LoginUserVo {

  private Long id;

  private String account;

  private String nickname;

  private String avatar;

}
