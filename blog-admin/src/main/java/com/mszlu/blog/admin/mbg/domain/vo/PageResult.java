package com.mszlu.blog.admin.mbg.domain.vo;

import java.util.List;
import lombok.Data;

/**
 * @author wx
 */
@Data
public class PageResult<T> {

  private List<T> list;

  private  Long total;

}
