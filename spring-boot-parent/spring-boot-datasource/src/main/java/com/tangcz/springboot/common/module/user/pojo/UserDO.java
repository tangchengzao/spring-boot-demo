package com.tangcz.springboot.common.module.user.pojo;

import com.tangcz.springboot.common.data.beans.RouteBaseDO;

import java.util.Date;

/**
 * ClassName:UserDO
 * Package:com.tangcz.springboot.common.module.user.pojo
 * Description:
 *
 * @date:2020/6/6 19:27
 * @author:tangchengzao
 */
public class UserDO extends RouteBaseDO {

    /**
     * 账号
     */
    private String            account;

    /**
     * 密码
     */
    private String            password;

    /**
     * 昵称
     */
    private String            nickName;

    /**
     * 说明
     */
    private String            summary;

    /**
     * 头像地址
     */
    private String            logoUrl;

    /**
     * 邀请人
     */
    private long              inviterId;

    /**
     * 用户等级
     */
    private int               level;

    /**
     * 等级过期时间
     */
    private Date levelExpiredTime;

    /**
     * 联系人
     */
    private String            contacts;

    /**
     * 邮箱
     */
    private String            email;

    /**
     * 经营类型
     */
    private Integer           businessType;

    /**
     * 证件类型
     */
    private Integer           certificateType;

    /**
     * 证件照片
     */
    private String            certificatePhoto;

    /**
     * 店铺名称
     */
    private String            shopName;

    /**
     * 店铺链接
     */
    private String            shopUrl;

    /**
     * 用户渠道
     */
    private String            channel;

    /**
     * 账号状态
     */
    private Integer           auditStatus;

    /**
     * 最后登录时间
     */
    private Date              lastLoginTime;

    /**
     * 最后访问时间
     */
    private Date              lastVisitTime;

    /**
     * 最后修改密码时间
     */
    private Date              lastPwdChangeTime;

    /**
     * 三方渠道用户
     */
    private int               type;

    /**
     * 是否已下过单 0：未下过，1：下过
     */
    private int               ordered;

    /**
     * 银行卡号
     */
    private String            bankAccount;

    /**
     * 开户行
     */
    private String            bankName;

    /**
     * 开户人
     */
    private String            accountOpener;

    /**
     * 联系人
     */
    private String            contactsPhone;

    /**
     * 禁用
     */
    private int               isDisable;

    /**
     * 用户类型
     */
    private int               userType;

    /**
     * 备注
     */
    private String            remark;

    /**
     * 登录账号
     */
    private String            loginAccount;

    /**
     * 邀请码
     */
    private String            invitationCode;

    /**
     * 微信昵称
     */
    private String            wxNickName;

    /**
     * 邀请码创建者
     */
    private String            invitationCodeCreator;

    /**
     * 注册平台
     */
    private int               registerPlatform;

    /**
     * 身份类型
     */
    private int               identityType;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getLevelExpiredTime() {
        return levelExpiredTime;
    }

    public void setLevelExpiredTime(Date levelExpiredTime) {
        this.levelExpiredTime = levelExpiredTime;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificatePhoto() {
        return certificatePhoto;
    }

    public void setCertificatePhoto(String certificatePhoto) {
        this.certificatePhoto = certificatePhoto;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public Date getLastPwdChangeTime() {
        return lastPwdChangeTime;
    }

    public void setLastPwdChangeTime(Date lastPwdChangeTime) {
        this.lastPwdChangeTime = lastPwdChangeTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setInviterId(long inviterId) {
        this.inviterId = inviterId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountOpener() {
        return accountOpener;
    }

    public void setAccountOpener(String accountOpener) {
        this.accountOpener = accountOpener;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public int getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(int isDisable) {
        this.isDisable = isDisable;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getInvitationCodeCreator() {
        return invitationCodeCreator;
    }

    public void setInvitationCodeCreator(String invitationCodeCreator) {
        this.invitationCodeCreator = invitationCodeCreator;
    }

    public int getRegisterPlatform() {
        return registerPlatform;
    }

    public void setRegisterPlatform(int registerPlatform) {
        this.registerPlatform = registerPlatform;
    }

    public int getIdentityType() {
        return identityType;
    }

    public void setIdentityType(int identityType) {
        this.identityType = identityType;
    }

}
