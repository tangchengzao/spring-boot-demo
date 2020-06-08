package com.tangcz.springboot.startup;

import com.tangcz.springboot.mybatis.config.DynamicDataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * ClassName:Application
 * Package:com.tangcz.springboot.startup
 * Description:
 *
 * @date:2020/6/6 19:09
 * @author:tangchengzao
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = "com.tangcz.springboot")
@Import({DynamicDataSourceConfig.class})
@MapperScan("com.tangcz.springboot.common.module.*.dao")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("test");
    }

}
