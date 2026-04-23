package com.mszlu.blog.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回类
 *
 * @author wx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {

  private boolean success;

  private Integer code;

  private String msg;

  private Object data;

  public static CommonResult success(Object data) {
    return new CommonResult(true, 200, "success", data);
  }

  public static CommonResult fail(Integer code, String msg) {
    return new CommonResult(false, code, msg, null);
  }


}
