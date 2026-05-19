package com.mszlu.blog.admin.mbg.domain.vo.params;

import lombok.Data;

/**
 * 分页
 *
 * @author wx
 */
@Data
public class PageParam {

  private Integer currentPage;

  private Integer pageSize;

  private String queryString;

}
