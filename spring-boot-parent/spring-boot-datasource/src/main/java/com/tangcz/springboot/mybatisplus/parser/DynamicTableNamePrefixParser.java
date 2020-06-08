package com.tangcz.springboot.mybatisplus.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.core.toolkit.TableNameParser;
import com.tangcz.springboot.common.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName:DynamicTableNameParser
 * Package:com.tangcz.springboot.mybatisplus.parser
 * Description:
 *
 * @date:2020/6/7 21:12
 * @author:tangchengzao
 */
public class DynamicTableNamePrefixParser implements ISqlParser {
    private static final String MYSQL       = "MYSQL";

    public static final String TABLE_PREFIX = "AU_";

    private Set<String> ignoreTables = new HashSet<>();
    private AtomicBoolean INIT = new AtomicBoolean();

    public void init() {
        if (INIT.get()) {
            return;
        }
        synchronized (this) {
            if (INIT.compareAndSet(false, true)) {
                ignoreTables.add("AU_DATASOURCE");
                ignoreTables.add("AU_ROUTE_RULE");
                ignoreTables.add("AU_APP_AUTH");
                ignoreTables.add("AU_CONFIG");
                ignoreTables.add("AU_CURRENCY_EXCHANGE_RATE");
            }
        }
    }

    protected String getTablePrefix() {
        return formatTablePrefix(RequestContext.getTablePrefix());
    }

    public static String formatTablePrefix(String tablePrefix) {
        if (tablePrefix == null) {
            return null;
        }
        tablePrefix = StringUtils.trim(tablePrefix);
        if (StringUtils.isNotBlank(tablePrefix) && !tablePrefix.endsWith("_")) {
            tablePrefix = tablePrefix + "_";
        }
        return StringUtils.upperCase(tablePrefix);
    }

    @Override
    public SqlInfo parser(MetaObject metaObject, String sql) {
        return SqlInfo.newInstance().setSql(convert(sql, getTablePrefix()));
    }

    public String convert(String sql, String tablePrefix) {
        Collection<String> tables = (new TableNameParser(sql)).tables();
        for (String table : tables) {
            String dynamicTableName = null;
            if (StringUtils.startsWithIgnoreCase(table, TABLE_PREFIX) && !ignoreTables.contains(StringUtils.upperCase(table))) {
                dynamicTableName = tablePrefix + table.substring(TABLE_PREFIX.length());
            }
            if (dynamicTableName != null) {
                String regex = "(?<=\\s)\\Q" + table + "\\E(?=\\s)";
                sql = sql.replaceAll(regex, dynamicTableName);
            }
        }
        return sql;
    }
}
