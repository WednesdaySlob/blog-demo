package com.mszlu.blog.admin.mbg.domain.entity;

import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    
    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private Collection<? extends GrantedAuthority> authorities;
    
    // 自定义字段
    private String userId;
    private String email;
    private String phone;
    private String nickname;
    // 保存 Admin 的 ID
    private Long adminId;
    // 扩展属性
    private Map<String, Object> attributes;
}