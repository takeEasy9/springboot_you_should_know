package com.springboot.ysk.common.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author takeEasy9
 * @version 1.0.0
 * @description 用户信息 entity
 * @createDate 2023/8/24 1:55
 * @since 1.0.0
 */
public class User {

    @NotNull(message = "用户id不能为空")
    private Long usrId;

    @NotNull(message = "用户账号不能为空")
    @Size(min = 2, max = 16, message = "账号长度必须是2-16个字符")
    private String account;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-20个字符")
    private String password;

    @NotEmpty(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
        this.usrId = usrId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
