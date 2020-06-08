package com.tangcz.springboot.common.data.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:BaseDO
 * Package:com.tangcz.springboot.common.data.beans
 * Description:
 *
 * @date:2020/6/6 14:28
 * @author:tangchengzao
 */
public class BaseDO extends JsonPojo implements Serializable {

    public static final int DELETED     = 1;
    public static final int NOT_DELETED = 0;

    public static final int STATUS_OFF  = 0;
    public static final int STATUS_ON   = 1;

    /**
     * 主键ID
     */
    private long          id;

    /**
     * 创建时间
     */
    private Date          createTime;

    /**
     * 修改时间
     */
    private Date          modifyTime;

    /**
     * 是否删除，0：正常，1：删除
     */
    private int           isDeleted;

    /**
     * 判断是否被删除
     */
    public boolean isDeleted() {
        return isDeleted != 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

}
