package com.laity.backstage.system.utils;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.List;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName PropertySource
 * @Description spring boot 中 yml 、yaml 对应的加载类为 YamlPropertySourceLoader。
 * spring-boot更新到1.5.2版本后locations属性无法使用
 * @PropertySource注解只可以加载proprties文件,无法加载yaml文件
 *  故现在把数据放到application.yml文件中,spring-boot启动时会加载
 * @PropertySource 注解读取属性文件的关键在于 PropertySourceFactory 接口中的 createPropertySource 方法，
 *  加载yaml配置文件的方法
 * 所以我们想要实现 @PropertySource 注解读取 yml 文件就需要实现 createPropertySource 方法，
 * 在 @PropertySource 注解其是通过 DefaultPropertySourceFactory 类来实现这个方法，我们只需要继承此类，
 * 并重写其 createPropertySource 方法即可，
 */
public class YmlFactoryUtil extends DefaultPropertySourceFactory {
    @Override
    public org.springframework.core.env.PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (resource == null){
            return super.createPropertySource(name, resource);
        }
        List<org.springframework.core.env.PropertySource<?>> sources = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        return sources.get(0);
    }
}
