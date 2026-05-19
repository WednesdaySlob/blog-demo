package com.mszlu.blog.common.utils;

import com.alibaba.fastjson2.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 七牛云 文件
 * @author wx
 */
@Component
public class QiniuUtils {

  public static  final String url = "https://static.mszlu.com/";


  @Value("${oss.qiniu.accessKey}")
  private String accessKey;

  @Value("${oss.qiniu.secretKey}")
  private String accessSecretKey;

  public boolean upload(MultipartFile file, String fileName) {
    // 构造一个带指定 Region 对象的配置类（使用 create 方法替代 new）
    Configuration cfg = Configuration.create(Region.createWithRegionId("z1"));
    UploadManager uploadManager = new UploadManager(cfg);
    // ...生成上传凭证，然后准备上传
    String bucket = "blogmszlu";
    // 默认不指定key的情况下，以文件内容的hash值作为文件名
    try {
      byte[] uploadBytes = file.getBytes();
      // 注意：你代码中写的是 accessSecretKey，建议统一命名
      Auth auth = Auth.create(accessKey, accessSecretKey);
      String upToken = auth.uploadToken(bucket);
      Response response = uploadManager.put(uploadBytes, fileName, upToken);
      // 解析上传成功的结果
      DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }

}
