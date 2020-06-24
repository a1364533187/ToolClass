package com.bigcow.spring.spring_bean.interesting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 写一个类，实现ImportBeanDefinitionRegistrar，这个接口可以帮我们把一个BeanDefinition放入Spring的map
 * 写在这里spring 不认识，需要注入, 通过注解Import
 */
public class MyBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
            BeanDefinitionRegistry beanDefinitionRegistry) {
        List<Class> mappers = new ArrayList<Class>() {

            {
                add(HiService.class);
                add(HelloService.class);
            }
        };
        for (Class clazz : mappers) {

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MyFactoryBean2.class);
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) builder
                    .getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
            System.out.println("--->" + clazz.getName());
            beanDefinitionRegistry.registerBeanDefinition(clazz.getName(), beanDefinition);
        }

    }
}
