package com.mszlu.blog.controller;

import com.mszlu.blog.common.api.CommonResult;
import com.mszlu.blog.common.utils.QiniuUtils;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文章图片上传
 *
 * @author wx
 */
@RestController
@RequestMapping("upload")
@RequiredArgsConstructor
public class uploadController {

  private final QiniuUtils qiniuUtils;

  @PostMapping
  public CommonResult upload(@RequestParam("image") MultipartFile file) {

    String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(
        file.getOriginalFilename(), ",");
    boolean upload = qiniuUtils.upload(file, fileName);
    if (upload) {
      return CommonResult.success(QiniuUtils.url + fileName);
    }
    return CommonResult.fail(20001,"上传失败");

  }

}
