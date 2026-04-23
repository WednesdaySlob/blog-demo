package com.mszlu.blog.service.impl;

import com.mszlu.blog.VO.TagVO;
import com.mszlu.blog.mbg.domain.entity.Tag;
import com.mszlu.blog.mbg.mapper.TagMapper;
import com.mszlu.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

  private final TagMapper tagMapper;


  @Override
  public List<TagVO> findTagsByArticleId(Long id) {
    List<Tag> tags = tagMapper.findTagsByArticleId(id);
    return copyList(tags);
  }

  private List<TagVO> copyList(List<Tag> tagList) {
    List<TagVO> tagVOList = new ArrayList<>();
    for (Tag tag : tagList) {
      tagVOList.add(copy(tag));
    }
    return tagVOList;
  }

  private TagVO copy(Tag tag) {
    TagVO tagVO = new TagVO();
    BeanUtils.copyProperties(tag,tagVO);
    return tagVO;
  }
}
