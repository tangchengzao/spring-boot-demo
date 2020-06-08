package com.tangcz.springboot.mybatis;

import com.tangcz.springboot.common.util.MD5Util;
import com.tangcz.springboot.common.util.StringUtil;

/**
 * ClassName:JdbcConfig
 * Package:com.tangcz.springboot.mybatis
 * Description:
 *
 * @date:2020/6/6 14:30
 * @author:tangchengzao
 */
public class JdbcConfig {

    /**
     * DB连接字符串
     */
    private String jdbcUrl;
    /**
     * 连接用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;

    public String buildIdentify() {
        return MD5Util.getMD5Format(StringUtil.formatArg("#", jdbcUrl, userName, password));
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
