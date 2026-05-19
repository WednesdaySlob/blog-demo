package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.mbg.domain.vo.TagVO;
import com.mszlu.blog.mbg.domain.entity.Tag;
import com.mszlu.blog.mbg.mapper.TagMapper;
import com.mszlu.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

  @Override
  public List<TagVO> hot(int limit) {
    List<Long> hotTagIds =  tagMapper.findHotsTagIds(limit);
    if (CollectionUtils.isEmpty(hotTagIds)){
      return Collections.emptyList();
    }
    List<Tag> tagList = tagMapper.findTagsByTagIds(hotTagIds);

    return copyList(tagList);
  }

  @Override
  public CommonResult findAllDetail() {
//    List<Tag> tags = baseMapper.selectList(new LambdaQueryWrapper<>());
    List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
    return CommonResult.success(copyList(tags));
  }

  @Override
  public CommonResult findDetailById(Long id) {
    Tag tag = tagMapper.selectById(id);
    TagVO tagVO = copy(tag);
    return CommonResult.success(tagVO);
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
