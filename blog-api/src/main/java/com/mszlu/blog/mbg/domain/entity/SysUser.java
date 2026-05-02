package com.mszlu.blog.mbg.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wx
 * @since 2026-04-22
 */

@Data
@TableName("ms_sys_user")
@ApiModel(value = "SysUser对象", description = "")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    @TableField("account")
    @ApiModelProperty("账号")
    private String account;

    /**
     * 是否管理员
     */
    @TableField("admin")
    @ApiModelProperty("是否管理员")
    private Integer admin;

    /**
     * 头像
     */
    @TableField("avatar")
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 注册时间
     */
    @ApiModelProperty("注册时间")
    @TableField("create_date")
    private Long createDate;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField("deleted")
    @ApiModelProperty("是否删除")
    private Integer deleted;

    /**
     * 邮箱
     */
    @TableField("email")
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 最后登录时间
     */
    @TableField("last_login")
    @ApiModelProperty("最后登录时间")
    private Long lastLogin;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @TableField("mobile_phone_number")
    private String mobilePhoneNumber;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @TableField("nickname")
    private String nickname;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    /**
     * 加密盐
     */
    @TableField("salt")
    @ApiModelProperty("加密盐")
    private String salt;

    /**
     * 状态
     */
    @TableField("status")
    @ApiModelProperty("状态")
    private String status;
}
