package com.tangcz.springboot.test;

import com.tangcz.springboot.common.module.config.pojo.DataSourceDO;
import com.tangcz.springboot.common.module.config.service.DataSourceService;
import com.tangcz.springboot.common.module.user.pojo.UserDO;
import com.tangcz.springboot.common.module.user.service.UserService;
import com.tangcz.springboot.mybatis.util.DBContextHolder;
import com.tangcz.springboot.startup.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName:DynamicDataSourceTest
 * Package:com.tangcz.springboot.test
 * Description:
 *
 * @date:2020/6/6 19:48
 * @author:tangchengzao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DynamicDataSourceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private DataSourceService dataSourceService;

    @Test
    public void test() {
        DBContextHolder.setDataSourceId("1");
        UserDO userDO0 = userService.findById(7702);
        System.out.println(userDO0.getAccount());
        DBContextHolder.setDataSourceId(null);
        DataSourceDO dataSource = dataSourceService.findDataSource(2);
        System.out.println(dataSource.getDatasource());
    }

}
