package com.liang.netty.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 获取Bean
 */
@Slf4j
@Component
public class SpringUtil implements ApplicationContextAware {

    private volatile static ConfigurableApplicationContext myApplicationContext;

    /**
     * 重写父类方法
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (this){
            if(myApplicationContext == null){
                myApplicationContext = (ConfigurableApplicationContext) applicationContext;
            }
        }
    }

    /**
     * 获取上下文对象
     */
    public static ConfigurableApplicationContext getApplicationContext() {
        return myApplicationContext;
    }

    /**
     * 通过名称获取bean
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取bean
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 替换上下文中的bean
     */
    public static <T> boolean replaceBean(Class oldCls, Class<T> newCls, Object... args){
        boolean bool = false;
        String oldBeanName = null;
        try {
            if (oldCls != null && newCls != null){
                //获取旧Bean的名字
                oldBeanName = new StringBuilder()
                        .append(Character.toLowerCase(oldCls.getSimpleName().charAt(0)))
                        .append(oldCls.getSimpleName().substring(1)).toString();
                log.info("将要替换的Bean为：{}.", oldBeanName);
                //获取上下文对象
                ConfigurableApplicationContext applicationContext = getApplicationContext();
                if (applicationContext.getBean(oldBeanName) != null){
                    //包装Bean
                    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(newCls);
                    //构造方法的必要参数，顺序和类型要求和clazz中定义的一致
                    for (Object arg : args) {
                        beanDefinitionBuilder.addConstructorArgValue(arg);
                    }
                    //得到Bean工厂并替换Bean
                    BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
                    beanFactory.registerBeanDefinition(oldBeanName, beanDefinitionBuilder.getBeanDefinition());
                    bool = true;
                }
            }
        }catch (Exception ex){
            log.error("替换bean -->" + oldBeanName + " 异常：" + ex.getMessage(), ex);
        }finally {
            return bool;
        }
    }
}
