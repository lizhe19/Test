package com.test.mybatus.inteceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Properties;

/**
 * @author lz-119612
 * @version 1.0
 * @date 2020/9/4 9:58
 *
 * 不同拦截器顺序：
 *        Executor -> ParameterHandler -> StatementHandler -> ResultSetHandler
 *  
 **/
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class }),
        //先查缓存 再查数据库
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        //查询
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class InterceptorTest implements Interceptor {

    private static final String REGEX = "\\s*insert\\u0020.*|\\s*delete\\u0020.*|\\s*update\\u0020.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取执行参数
        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];
        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("[\\t\\n\\r]", " ");
        String sqlId = ms.getId();
        String className = sqlId.substring(0, sqlId.lastIndexOf("."));
        System.out.println("打印SQL语句="+sql);
        log.info("sqlId={},className={}",sqlId,className);
        //TableSeg自定义注解
        /*Class<?> classObj = Class.forName(className);

        TableSeg tableSeg = classObj.getAnnotation(TableSeg.class);
        if(null == tableSeg){
            //不需要分表，直接传递给下一个拦截器处理
            return invocation.proceed();
        }
        //根据配置获取分表字段，生成分表SQL
        String accountMonth = genShardByValue(metaStatementHandler, mappedStatement ,tableSeg, boundSql);
        String newSql = boundSql.getSql().replace(tableSeg.tableName(), tableSeg.tableName() + "_" + accountMonth);*/

        if (sql.matches(REGEX)) {
            System.out.println("update 语句");
        } else {
            System.out.println("select 语句");
            //通过反射修改sql语句
            Field field = boundSql.getClass().getDeclaredField("sql");
            field.setAccessible(true);
            field.set(boundSql, sql);
        }
        return invocation.proceed();
    }
    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }
    @Override
    public void setProperties(Properties properties) {
        log.info("***************{}", properties.toString());
    }
}
