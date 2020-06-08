package com.tangcz.springboot.mybatis.annotation;

import java.lang.annotation.*;

/**
 * ClassName:ShardOnConfigDB
 * Package:com.tangcz.springboot.mybatis.annotation
 * Description:
 *
 * @date:2020/6/6 14:03
 * @author:tangchengzao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ShardOnConfigDB {
}
