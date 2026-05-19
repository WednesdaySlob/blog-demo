package com.mszlu.blog.admin.mbg.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author wx
 */
@Data
public class Admin {
  @TableId(type = IdType.AUTO)
  private Long id;

  private String username;

  private String password;

  private String role;

  //  1代表启用，0代表禁用
  private Integer status;
}
