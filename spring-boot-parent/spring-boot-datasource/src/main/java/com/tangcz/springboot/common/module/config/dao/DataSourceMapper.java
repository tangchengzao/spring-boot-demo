package com.tangcz.springboot.common.module.config.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tangcz.springboot.common.module.config.pojo.DataSourceDO;
import com.tangcz.springboot.mybatis.annotation.ShardOnConfigDB;

import java.util.List;

/**
 * ClassName:DataSourceMapper
 * Package:com.tangcz.springboot.common.module.config.dao
 * Description:
 *
 * @date:2020/6/6 14:59
 * @author:tangchengzao
 */
@ShardOnConfigDB
public interface DataSourceMapper extends BaseMapper<DataSourceDO> {

    List<DataSourceDO> selectAll();

}
