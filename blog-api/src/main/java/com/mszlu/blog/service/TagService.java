package com.mszlu.blog.service;

import com.mszlu.blog.VO.TagVO;
import com.mszlu.blog.mbg.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
public interface TagService extends IService<Tag> {

  List<TagVO> findTagsByArticleId(Long id);
}
