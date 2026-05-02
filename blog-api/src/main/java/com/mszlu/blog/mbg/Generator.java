package com.mszlu.blog.mbg;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.sql.Types;
import java.util.Collections;

/**
 * mybatis-plus 代码生成器
 *
 * @author wx
 */
public class Generator {

  public static void main(String[] args) {
    // 基础路径
    String basePath = "D:\\wx\\桌面\\blog\\blog-parent\\blog-api";
    FastAutoGenerator.create(
            "jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=UTF-8&serverTimeZone=UTC",
            "root", "123456")
        .globalConfig(builder -> {
          builder.author("wx") // 设置作者
              .enableSwagger() // 开启 swagger 模式
              .outputDir(basePath + "\\src\\main\\java") // Java文件输出目录
              .commentDate("yyyy-MM-dd");
//              .fileOverride(); // 覆盖已生成文件
        })
        .dataSourceConfig(builder ->
            builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
              int typeCode = metaInfo.getJdbcType().TYPE_CODE;
              if (typeCode == Types.SMALLINT) {
                // 自定义类型转换
                return DbColumnType.INTEGER;
              }
              return typeRegistry.getColumnType(metaInfo);
            })
        )
        .packageConfig(builder ->
            builder.parent("com.mszlu.blog") // 父包名
                .entity("entity") // 实体类包名
                .service("service") // Service包名
                .serviceImpl("service.impl") // Service实现类包名
                .mapper("mapper") // Mapper接口包名
                .controller("controller") // Controller包名
                .pathInfo(Collections.singletonMap(
                    OutputFile.xml,
                    basePath + "\\src\\main\\resources\\mapper"
                )) // XML文件输出目录SQLQuery
        )
        .strategyConfig(builder ->
            builder.addInclude("ms_comment") // 设置需要生成的表名
                .addTablePrefix("ms_") // 设置过滤表前缀
                .entityBuilder() // 实体类配置
                .enableLombok() // 启用Lombok
                .logicDeleteColumnName("deleted") // 逻辑删除字段
                .enableTableFieldAnnotation() // 开启字段注解
                .mapperBuilder()
                .enableBaseResultMap() // 启用BaseResultMap
                .enableBaseColumnList() // 启用BaseColumnList
                .serviceBuilder()
                .formatServiceFileName("%sService") // 格式化Service接口文件名
                .formatServiceImplFileName("%sServiceImpl") // 格式化Service实现类文件名
                .controllerBuilder()
                .enableRestStyle() // 开启RestController
                .enableHyphenStyle() // 启用驼峰转连字符
        )
        .templateConfig(builder ->
            builder.controller("/mbg/controller.java")
                .entity("/mbg/entity.java")
        )
        // 使用Freemarker引擎模板
        .templateEngine(new FreemarkerTemplateEngine())
        .execute();

  }

}
