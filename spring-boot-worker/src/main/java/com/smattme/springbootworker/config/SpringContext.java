package com.smattme.springbootworker.config;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;


    /**
     * Returns the Spring managed bean instance of the given class type if it exists.
     * Returns null otherwise.
     * @param beanClass to fetch
     * @return Object of type T
     */
    public static <T extends Object> T getBean(Class<T> beanClass) {
        if(Objects.isNull(context)) throw new RuntimeException("Application context is not fully booted");
        return context.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
