package com.spring.cloud.feign.tly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author tianluye
 * @param <T>
 */
public class ProxyClientFactoryBean<T> implements FactoryBean<T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Class<T> interfaceType;

    public ProxyClientFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() throws Exception {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        InvocationHandler handler = new ServiceProxy<>(interfaceType);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[] {interfaceType}, handler);
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    class ServiceProxy<T> implements InvocationHandler {

        private Class<T> interfaceType;

        public ServiceProxy(Class<T> interfaceType) {
            this.interfaceType = interfaceType;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            logger.info("调用前，参数：{}", Arrays.asList(args));
            // 这里可以得到参数数组和方法等，可以通过反射，注解等，进行结果集的处理
            // mybatis就是在这里获取参数和相关注解，然后根据返回值类型，进行结果集的转换
            Object result = (int)args[0] + (int)args[1];
            logger.info("调用后，结果：{}", result);
            return result;
        }
    }

}
