package com.spring.cloud.feign.tly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author tian.luye
 */
public class ProxyClientRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void setEnvironment(@Nullable Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(@Nullable ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        // 注册缺省配置到容器 registry（只是将 EnableFeignClients的逻辑拿过来，再本次 demo中没有任何意义）
        registerDefaultConfiguration(metadata, registry);
        // 注册所发现的各个 proxy客户端到到容器 registry
        registerFeignClients(metadata, registry);
    }

    /**
     * 注册 feign客户端缺省配置
     *
     * @param metadata @EnableProxy注解上的元数据
     * @param registry bean定义工厂
     */
    private void registerDefaultConfiguration(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        // 获取注解 @EnableProxy的注解属性
        Map<String, Object> defaultAttrs = metadata.getAnnotationAttributes(EnableProxy.class.getName(), true);
        logger.info("获取注解【EnableProxy】上的属性: {}", defaultAttrs);
        if (defaultAttrs != null && defaultAttrs.containsKey("defaultConfiguration")) {
            String name;
            if (metadata.hasEnclosingClass()) {
                //  针对注解元数据 metadata对应一个内部类或者方法返回的方法本地类的情形
                name = "default." + metadata.getEnclosingClassName();
            } else {
                // 这里 xxx.FeignApplication 是注解 @EnableProxy所在配置类的长名称
                name = "default." + metadata.getClassName();
            }
            // default.com.spring.cloud.feign.FeignApplication
            logger.info("name属性值为: {}", name);
            // 构建一个 bean对象，包含了注解 @EnableProxy注解标注的配置类信息属性，注册到 Spring容器中
            this.registerClientConfiguration(registry, name, defaultAttrs.get("defaultConfiguration"));
        }
    }

    /**
     * 将名称为 name，属性值为 configuration包装的对象注入到 Spring容器中
     *
     * @param registry      bean注册工厂
     * @param name          bean名称
     * @param configuration 自定义配置类
     */
    private void registerClientConfiguration(BeanDefinitionRegistry registry, Object name, Object configuration) {
        // 建造器模式创建 bean对象
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ProxyClientSpecification.class);
        builder.addConstructorArgValue(name);
        builder.addConstructorArgValue(configuration);
        String beanName = name + "." + ProxyClientSpecification.class.getSimpleName();
        logger.info("动态的为 Spring容器中添加一个 bean: {}", beanName);
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }

    /**
     * 将被 @ProxyClient注解标注的接口，通过动态代理生成 bean对象，注册到 Spring容器中
     *
     * @param metadata 注解元数据
     * @param registry bean注册工厂实例
     */
    private void registerFeignClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        // 定义一个基于 classpath的组件扫描器，找出被 @ProxyClient标注的客户端
        ClassPathScanningCandidateComponentProvider scanner = this.getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(ProxyClient.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        // 获取包扫描路径，去扫描 @ProxyClient标注的客户端
        Set<String> basePackages = this.getBasePackages(metadata);
        Iterator<String> var17 = basePackages.iterator();
        while (var17.hasNext()) {
            String basePackage = var17.next();
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            Iterator<BeanDefinition> var21 = candidateComponents.iterator();
            while (var21.hasNext()) {
                try {
                    BeanDefinition definition = var21.next();
                    String className = definition.getBeanClassName();
                    GenericBeanDefinition beanDefinition = (GenericBeanDefinition) definition;
                    // 在这里，我们可以给该对象的属性注入对应的实例。
                    // 比如 mybatis，就在这里注入了 dataSource和 sqlSessionFactory，
                    // 则 FactoryBean中需要提供包含该属性的构造方法，否则会注入失败
                    Class beanClazz = Class.forName(className);
                    beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanClazz);
                    // 注意，这里的BeanClass是生成Bean实例的工厂，不是Bean本身。
                    // FactoryBean是一种特殊的Bean，其返回的对象不是指定类的一个实例，
                    // 其返回的是该工厂Bean的 getObject方法所返回的对象。
                    beanDefinition.setBeanClass(ProxyClientFactoryBean.class);
                    // 为代理类设置注解 @ProxyClient注解里面的属性
                    // 注意，FeignClient也有 Configuration属性，在这个地方进行 bean注册
                    ProxyClient annotation = (ProxyClient) beanClazz.getAnnotation(ProxyClient.class);
                    beanDefinition.setPrimary(annotation.primary());
                    // 这里采用的是byType方式注入，类似的还有byName等
                    beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
                    registry.registerBeanDefinition(className, definition);
                } catch (Exception e) {
                    logger.error(e.toString());
                }
            }
        }

    }

    /**
     * 定义一个基于classpath的组件扫描器
     *
     * @return 组件扫描器
     */
    private ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                // 自定义过滤匹配规则
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent() && !beanDefinition.getMetadata().isAnnotation()) {
                    isCandidate = true;
                }
                return isCandidate;
            }
        };
    }

    /**
     * 根据 @EnableProxy注解的属性，找到包扫描路径
     *
     * @param importingClassMetadata 元注解
     * @return 包扫描路径
     */
    private Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableProxy.class.getCanonicalName());
        Set<String> basePackages = new HashSet<>();
        String[] var4 = (String[])((String[])attributes.get("value"));
        int var5 = var4.length;
        int var6;
        String pkg;
        for(var6 = 0; var6 < var5; ++var6) {
            pkg = var4[var6];
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        var4 = (String[])((String[])attributes.get("basePackages"));
        var5 = var4.length;
        for(var6 = 0; var6 < var5; ++var6) {
            pkg = var4[var6];
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }
        return basePackages;
    }


}