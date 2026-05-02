package com.mszlu.blog.service;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.vo.params.RegisterParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * 注册 接口
 * @author wx
 */
@Transactional
public interface RegisterService {

  /**
   * 注册
   * @param registerParam
   * @return
   */
  CommonResult register(RegisterParam registerParam);

}
