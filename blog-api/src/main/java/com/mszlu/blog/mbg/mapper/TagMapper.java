package com.mszlu.blog.mbg.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.mbg.domain.entity.Tag;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */
public interface TagMapper extends BaseMapper<Tag> {

  List<Tag> findTagsByArticleId(Long articleId);

//  default List<Tag> findTagsByArticleId(Long articleId) {
//    LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
//    wrapper.inSql(Tag::getId,
//        "select tag_id from ms_article_tag where article = " + articleId
//    );
//    return selectList(wrapper);
//  }
}
