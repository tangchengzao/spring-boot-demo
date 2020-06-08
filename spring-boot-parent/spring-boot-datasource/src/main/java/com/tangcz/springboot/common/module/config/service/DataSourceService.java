package com.tangcz.springboot.common.module.config.service;

import com.tangcz.springboot.common.module.config.pojo.DataSourceDO;

/**
 * ClassName:DataSourceService
 * Package:com.tangcz.springboot.common.module.config.service
 * Description:
 *
 * @date:2020/6/6 19:52
 * @author:tangchengzao
 */
public interface DataSourceService {

    DataSourceDO findDataSource(long id);

}
