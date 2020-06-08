package com.tangcz.springboot.mybatis.annotation;

import com.tangcz.springboot.common.cache.ScriptLangEnum;
import com.tangcz.springboot.common.shard.enums.TableShardStrategyDefinitionEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ShardStrategy {

    /**
     * 分表策略指定<br>
     * 自动将$tableName$替换为分表
     */
    TableShardStrategyDefinitionEnum[] value() default {};

    /**
     * 表达式脚本，通过脚本返回SQL
     * </br>
     * 结合{@link #scriptLang()}设置正确的脚本语言（默认MVEL）
     * </br>
     * 可使用变量：
     * </br>Object[] args：方法入参
     * </br>String sql：原始SQL
     * </br>Object shardValue：分表参数
     * </br>
     * </br>示例：sql.replace("$user$", "user_" + (shardValue % 100))
     */
    String script() default StringUtils.EMPTY;

    /**
     * 脚本语言，默认MVEL
     */
    ScriptLangEnum scriptLang() default ScriptLangEnum.MVEL;

}
