package com.mszlu.blog.common.api;

import lombok.Data;

/**
 * 分页数据封装类
 * @author wx
 */
@Data
public class CommonPage {

  private int page = 1;

  private int pageSize = 10;

  private Long categoryId;

  private  Long tagId;

  private String year;

  private String month;

  //
  public String getMonth(){
    if (this.month != null && this.month.length() == 1){
      return "0" + this.month;
    }
    return this.month;
  }

}
