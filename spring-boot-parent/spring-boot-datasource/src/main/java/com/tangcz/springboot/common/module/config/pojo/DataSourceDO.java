package com.tangcz.springboot.common.module.config.pojo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tangcz.springboot.common.data.beans.BaseDO;
import com.tangcz.springboot.mybatis.JdbcConfig;

/**
 * ClassName:DataSourceDO
 * Package:com.tangcz.springboot.common.module.config.pojo
 * Description:
 *
 * @date:2020/6/6 14:29
 * @author:tangchengzao
 */
@TableName("au_datasource")
public class DataSourceDO extends BaseDO {

    /**
     * 名称
     */
    private String            name;

    /**
     * 表前缀，默认无前缀
     */
    private String            tablePrefix;

    /**
     * 说明
     */
    @TableField(value = "`desc`")
    private String            desc;

    /**
     * 数据库连接配置，JSON格式，见JdbcConfig
     */
    private String            datasource;

    public JdbcConfig parseDataSource() {
        return JSON.parseObject(datasource, JdbcConfig.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

}
