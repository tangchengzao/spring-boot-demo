package com.tangcz.springboot.common.module.config.service.impl;

import com.tangcz.springboot.common.module.config.dao.DataSourceMapper;
import com.tangcz.springboot.common.module.config.pojo.DataSourceDO;
import com.tangcz.springboot.common.module.config.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:DataSourceServiceImpl
 * Package:com.tangcz.springboot.common.module.config.service.impl
 * Description:
 *
 * @date:2020/6/6 19:52
 * @author:tangchengzao
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    public DataSourceDO findDataSource(long id) {
        return dataSourceMapper.selectById(id);
    }
}
